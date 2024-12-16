package hexlet.code.controller;

import hexlet.code.dto.urls.MainPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import hexlet.code.repository.UrlRepository;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import okhttp3.HttpUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;



import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public UrlController(HttpUrl url) throws SQLException { }

    public static void addUrlHandler(Context ctx) {
        String url = ctx.formParam("url");

        try {
            URL parsedUrl = new URI(url).toURL();
            String domain = parsedUrl.getProtocol() + "://" + parsedUrl.getHost();
            if (parsedUrl.getPort() != -1) {
                domain += ":" + parsedUrl.getPort();
            }

            if (!UrlRepository.existsByName(domain)) {
                //var createdAt = LocalDateTime.now();
                Url currentUrl = new Url(domain);
                UrlRepository.save(currentUrl);
                ctx.sessionAttribute("flash", "Страница успешно добавлена.");
                System.out.println("GOOD ADDED");
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует.");
                System.out.println("GOOD NOT ADDED");
            }
            ctx.redirect(NamedRoutes.urlsPath());

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL.");
            ctx.redirect("/");
        }
    }

    //list of urls
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

    //main page
    public static void main(Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        var page = new MainPage(flash);

        ctx.render("urls/main.jte", model("page", page));
    }

    //show one url
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

    public static void checkUrlHandler(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.findByIndex(urlId).orElse(null);
        var check = new UrlCheck();

        if (url == null) {
            ctx.status(404).result("URL not found");
            return;
        }

        HttpResponse<String> response = Unirest.get(url.getName()).asString();
        int statusCode = response.getStatus();

        if (statusCode == 200) {
            String html = response.getBody();
            Document doc = Jsoup.parse(html);

            String title = doc.title();
            Element h1Element = doc.selectFirst("h1");
            String h1 = h1Element != null ? h1Element.text() : null;

            Element metaDescriptionElement = doc.selectFirst("meta[name=description]");
            String metaDescription = metaDescriptionElement != null ? metaDescriptionElement.attr("content") : null;

            check = new UrlCheck(urlId, statusCode, title, h1, metaDescription, LocalDateTime.now());
            System.out.println("Page 200 check will be added");
        } else {
            check = new UrlCheck(urlId, statusCode, LocalDateTime.now());
            System.out.println("Page NOT 200 check will be added");
        }

        UrlCheckRepository.save(check);
        System.out.println("Page check added successfully");
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
