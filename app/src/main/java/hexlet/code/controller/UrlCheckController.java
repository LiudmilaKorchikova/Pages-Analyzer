package hexlet.code.controller;

import io.javalin.http.Context;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.sql.SQLException;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;

public class UrlCheckController {

    public static void checkUrlHandler(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.findByIndex(urlId).orElse(null);

        if (url == null) {
            ctx.status(404).result("URL not found");
            return;
        }

        HttpResponse<String> response = Unirest.get(url.getName()).asString();
        UrlCheck check = createUrlCheck(urlId, response);

        UrlCheckRepository.save(check);
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }

    private static UrlCheck createUrlCheck(Long urlId, HttpResponse<String> response) {
        int statusCode = response.getStatus();
        if (statusCode == 200) {
            return createSuccessfulUrlCheck(urlId, response.getBody());
        } else {
            return createFailedUrlCheck(urlId, statusCode);
        }
    }

    private static UrlCheck createSuccessfulUrlCheck(Long urlId, String html) {
        Document doc = Jsoup.parse(html);
        String title = doc.title();
        Element h1Element = doc.selectFirst("h1");
        String h1 = h1Element != null ? h1Element.text() : null;

        Element metaDescriptionElement = doc.selectFirst("meta[name=description]");
        String metaDescription = metaDescriptionElement != null ? metaDescriptionElement.attr("content") : null;

        return new UrlCheck(urlId, 200, title, h1, metaDescription);
    }

    private static UrlCheck createFailedUrlCheck(Long urlId, int statusCode) {
        return new UrlCheck(urlId, statusCode);
    }
}
