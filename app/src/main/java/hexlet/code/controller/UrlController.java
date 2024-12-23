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

        try {
            URL parsedUrl = new URI(url).toURL();
            String domain = parsedUrl.getProtocol() + "://" + parsedUrl.getHost();
            if (parsedUrl.getPort() != -1) {
                domain += ":" + parsedUrl.getPort();
            }

            if (!UrlRepository.existsByName(domain)) {
                Url currentUrl = new Url(domain);
                UrlRepository.save(currentUrl);
                ctx.sessionAttribute("flash", "Страница успешно добавлена.");
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует.");
            }
            ctx.redirect(NamedRoutes.urlsPath());

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL.");
            ctx.redirect("/");
        }
    }

    public static void index(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        var urls = UrlRepository.getEntities();
        for (int i = 0; i < urls.size(); i++) {
            var url = urls.get(i);

            if (url == null) {
                ctx.status(404).result("URL not found");
                return;
            }


            var checks = UrlCheckRepository.getEntitiesForThisUrl(url.getId());
            url.setUrlChecks(checks);
        }
        var urlsPage = new UrlsPage(urls, flash);
        ctx.render("urls/index.jte", model("page", urlsPage));
    }

    public static void main(Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        var page = new MainPage(flash);

        ctx.render("urls/main.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findByIndex(id).orElse(null);
        if (url == null) {
            ctx.status(404).result("URL not found");
            return;
        }
        var checks = UrlCheckRepository.getEntitiesForThisUrl(id);
        url.setUrlChecks(checks);
        var page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));
    }
}
