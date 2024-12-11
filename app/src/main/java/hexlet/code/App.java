package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.utils.NamedRoutes;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.resolve.ResourceCodeResolver;

import hexlet.code.repository.BaseRepository;


public final class App {

    private static UrlRepository urlRepository;

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static Javalin getApp() throws Exception {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        String databaseUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL",
                "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");


        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseUrl);


        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        BaseRepository.dataSource = dataSource;


        app.get("/", ctx -> ctx.render("urls/main.jte"));

        app.post(NamedRoutes.urlsPath(), UrlController::addUrlHandler);

        app.get(NamedRoutes.urlsPath(), UrlController::index);

        return app;
    }

    public static void main(String[] args) throws Exception {
        Javalin app = getApp();
        app.start(getPort());
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
}
