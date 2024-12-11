package hexlet.code.controller;

import hexlet.code.dto.urls.MainPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import hexlet.code.repository.UrlRepository;



import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;


import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void addUrlHandler(Context ctx) {
        String url = ctx.formParam("url");

        try {
            URL parsedUrl = new URI(url).toURL();
            String domain = parsedUrl.getProtocol() + "://" + parsedUrl.getHost();
            if (parsedUrl.getPort() != -1) {
                domain += ":" + parsedUrl.getPort();
            }

            if (!UrlRepository.existsByName(domain)) {
                var createdAt = LocalDateTime.now();
                Url currentUrl = new Url(domain, createdAt);
                UrlRepository.save(currentUrl);
                ctx.sessionAttribute("flash", "Страница успешно добавлена.");
                System.out.println("GOOD ADDED");
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует.");
                System.out.println("GOOD NOT ADDED");
            }
            //var urls = UrlRepository.getEntities();
            //var page = new UrlsPage(urls);
            //page.setFlash(ctx.sessionAttribute("flash"));
            //ctx.render("urls/index.jte", model("page", page));
            ctx.redirect(NamedRoutes.urlsPath());

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL.");
            //System.out.println("BAD " + e.getMessage());
            //var page = new MainPage(ctx.sessionAttribute("flash"));
            //ctx.render("urls/main.jte", model("page", page));
            ctx.redirect("/");
        }
    }

    //list of urls
    public static void index(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        var urls = UrlRepository.getEntities();
        var urlsPage = new UrlsPage(urls, flash);
        ctx.render("urls/index.jte", model("page", urlsPage));
    }

    //main page
    public static void main(Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        var page = new MainPage(flash);

        ctx.render("urls/main.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findByIndex(id).orElseThrow(() -> new RuntimeException("URL not found"));
        var page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));
    }
}