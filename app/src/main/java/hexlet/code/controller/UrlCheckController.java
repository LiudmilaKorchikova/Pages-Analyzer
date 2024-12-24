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
import java.util.HashMap;
import java.util.Map;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;

import static hexlet.code.repository.BaseRepository.dataSource;

public class UrlCheckController {

    public static void checkUrl(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();

        UrlRepository.findById(urlId).ifPresentOrElse(url -> {
            try {
                HttpResponse<String> response = Unirest.get(url.getName()).asString();

                if (response.getStatus() == 200) {
                    UrlCheck check = parseHtml(urlId, response.getBody());
                    UrlCheckRepository.save(check);
                    ctx.redirect(NamedRoutes.urlPath(urlId));
                } else {
                    ctx.status(400).result("The URL is invalid or not accessible. HTTP Status: "
                            + response.getStatus());
                }
            } catch (UnirestException e) {
                ctx.status(400).result("The URL could not be reached. Please check the URL and try again.");
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, () -> ctx.status(404).result("URL not found"));
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

    public static Map<Long, UrlCheck> getLastChecks() throws SQLException {
        var sql = """
        WITH ranked_checks AS (
            SELECT
                id, url_id, status_code, title, h1, description, created_at,
                ROW_NUMBER() OVER (PARTITION BY url_id ORDER BY created_at DESC) AS row_num
            FROM url_checks
        )
        SELECT id, url_id, status_code, title, h1, description, created_at
        FROM ranked_checks
        WHERE row_num = 1;
            """;

        Map<Long, UrlCheck> lastChecks = new HashMap<>();

        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Long urlId = resultSet.getLong("url_id");
                UrlCheck check = new UrlCheck(
                        resultSet.getLong("id"),
                        resultSet.getInt("status_code"),
                        resultSet.getString("title"),
                        resultSet.getString("h1"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );
                lastChecks.put(urlId, check);
            }
        }
        return lastChecks;
    }
}
