package hexlet.code.controller;

import io.javalin.http.Context;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.sql.SQLException;
import java.time.LocalDateTime;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;

public class UrlCheckController {

    public static void checkUrl(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();

        var url = UrlRepository.findById(urlId);
        if (!url.isPresent()) {
            ctx.status(404).result("URL not found");
            return;
        }

        try {
            HttpResponse<String> response = Unirest.get(url.get().getName()).asString();
            UrlCheck check = parseHtml(urlId, response.getBody());
            UrlCheckRepository.save(check);
            ctx.redirect(NamedRoutes.urlPath(urlId));

        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес.");
            ctx.redirect(NamedRoutes.urlPath(urlId));
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static UrlCheck parseHtml(Long urlId, String html) {
        Document doc = Jsoup.parse(html);
        String title = doc.title();
        Element h1Element = doc.selectFirst("h1");
        String h1 = h1Element != null ? h1Element.text() : null;

        Element metaDescriptionElement = doc.selectFirst("meta[name=description]");
        String metaDescription = metaDescriptionElement != null ? metaDescriptionElement
                .attr("content") : null;
        var createdAt = LocalDateTime.now();
        return new UrlCheck(urlId, 200, title, h1, metaDescription, createdAt);
    }
}
