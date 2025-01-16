package hexlet.code.controller;

import io.javalin.http.Context;
import hexlet.code.repository.UrlRepository;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;

import hexlet.code.dto.urls.MainPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.utils.NamedRoutes;



import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void addUrlHandler(Context ctx) {
        String url = ctx.formParam("url");

        String domain = "";

        try {
            URL parsedUrl = new URI(url).toURL();
            domain = parsedUrl.getProtocol() + "://" + parsedUrl.getHost();
            if (parsedUrl.getPort() != -1) {
                domain += ":" + parsedUrl.getPort();
            }
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Invalid URL.");
            ctx.redirect("/");
            return;
        }

        try {
            if (!UrlRepository.existsByName(domain)) {
                Url currentUrl = new Url(domain);
                UrlRepository.save(currentUrl);
                ctx.sessionAttribute("flash", "The page has been successfully added.");
            } else {
                ctx.sessionAttribute("flash", "The page already exists.");
            }
            ctx.redirect(NamedRoutes.urlsPath());

        } catch (SQLException e) {
            ctx.sessionAttribute("flash", "Database error. Please try again later.");
            ctx.redirect("/");
        }
    }

    public static void index(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        var urls = UrlRepository.getEntities();

        var lastChecks = UrlCheckRepository.getLastChecks();

        var urlsPage = new UrlsPage(urls, flash, lastChecks);
        ctx.render("urls/index.jte", model("page", urlsPage));
    }

    public static void main(Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        var page = new MainPage(flash);

        ctx.render("urls/main.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();

        UrlRepository.findById(id).ifPresentOrElse(url -> {
            try {
                var checks = UrlCheckRepository.getChecksForUrl(id);

                var page = new UrlPage(url, checks);

                ctx.render("urls/show.jte", model("page", page));
            } catch (SQLException e) {
                ctx.status(500).result("Internal Server Error");
                e.printStackTrace();
            }
        }, () -> ctx.status(404).result("URL not found"));
    }
}
