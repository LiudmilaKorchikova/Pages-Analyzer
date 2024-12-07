package hexlet.code;


import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.resolve.ResourceCodeResolver;
/*import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinJte;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;*/


public final class App {

    private static UrlRepository urlRepository;

    public static Javalin getApp() throws Exception {
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 7000;


        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get("/add-url", ctx -> {
            ctx.render("/add-url"); // Страница добавления URL
        });

        // Обработчик для добавления URL
        app.post("/add-url", UrlController::addUrlHandler);

        // Маршрут для отображения всех URL
        app.get("/urls", UrlController::showUrlsHandler);

        app.get("/urls/:id", UrlController::showUrlByIdHandler);

        app.start(port);

        return app;
    }

    public static void main(String[] args) throws Exception {
        Javalin app = getApp();
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
}
