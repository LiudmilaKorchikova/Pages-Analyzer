package hexlet.code;


import io.javalin.Javalin;


public final class App {
    public static Javalin getApp() throws Exception {
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 7000;

        var app = Javalin.create(config -> config.bundledPlugins.enableDevLogging()).start(port);

        app.get("/", ctx -> ctx.result("Hello World"));

        return app;
    }

    public static void main(String[] args) throws Exception {
        Javalin app = getApp();
        app.start();
    }
}
