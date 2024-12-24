package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import io.javalin.http.Context;
import hexlet.code.repository.UrlRepository;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

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

            try {
                if (!UrlRepository.existsByName(domain)) {
                    var createdAt = LocalDateTime.now();
                    Url currentUrl = new Url(domain, createdAt);
                    UrlRepository.save(currentUrl);
                    ctx.sessionAttribute("flash", "Страница успешно добавлена.");
                } else {
                    ctx.sessionAttribute("flash", "Страница уже существует.");
                }
                ctx.redirect(NamedRoutes.urlsPath());

            } catch (SQLException e) {
                ctx.sessionAttribute("flash", "Ошибка базы данных. Попробуйте позже.");
                ctx.redirect("/");
            }

        } catch (URISyntaxException | MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный URL.");
            ctx.redirect("/");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Произошла непредвиденная ошибка.");
            ctx.redirect("/");
        }
    }

    public static void index(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        var urls = UrlRepository.getEntities();

        // Получаем последние проверки для всех URL
        Map<Long, UrlCheck> lastChecks = UrlCheckRepository.getLastChecks();

        // Присоединяем последние проверки к URL
        for (var url : urls) {
            UrlCheck lastCheck = lastChecks.get(url.getId());
            url.setLastUrlCheck(lastCheck);
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

        UrlRepository.findById(id).ifPresentOrElse(url -> {
            try {
                var checks = UrlCheckRepository.getChecksForUrl(id);
                url.setUrlChecks(checks);
                var page = new UrlPage(url);
                ctx.render("urls/show.jte", model("page", page));
            } catch (SQLException e) {
                ctx.status(500).result("Internal Server Error");
                e.printStackTrace();
            }
        }, () -> ctx.status(404).result("URL not found"));
    }
}
