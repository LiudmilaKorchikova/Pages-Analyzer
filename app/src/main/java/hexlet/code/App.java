package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.utils.NamedRoutes;
import hexlet.code.controller.UrlController;
import io.javalin.Javalin;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.resolve.ResourceCodeResolver;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

import hexlet.code.repository.BaseRepository;


public final class App {

    private static String databaseUrl;

    private static int getPort() {
        String port = System.getenv().getOrDefault("DB_PORT", "7070");
        return Integer.parseInt(port);
    }

    public static void initDatabase() throws Exception {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream("urls.sql");
        if (inputStream == null) {
            throw new Exception("SQL script not found in resources!");
        }

        String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        try (Connection connection = BaseRepository.dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
    }

    public static void clearDatabase() {
        try (Connection connection = BaseRepository.dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM url_checks");
            statement.executeUpdate("DELETE FROM urls");
        } catch (Exception e) {
            System.err.println("Failed to clear database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });


        databaseUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL",
                "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");



        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseUrl);


        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        BaseRepository.dataSource = dataSource;

        try {
            initDatabase();
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }


        app.get("/", UrlController::main);
        app.post(NamedRoutes.urlsPath(), UrlController::addUrlHandler);
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.post(NamedRoutes.urlChecksPath("{id}"), UrlController::checkUrlHandler);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            clearDatabase();
            app.stop();
        }));


        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
        if (databaseUrl.startsWith("jdbc:postgresql")) {
            System.out.println("PostgreSQL is being used JDBC URL: " + databaseUrl);
        } else {
            System.out.println("Local database is being used");
        }
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
}
