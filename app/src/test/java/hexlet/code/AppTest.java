package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    Javalin app;
    MockWebServer mockServer;

    @BeforeEach
    public final void setUp() {
        app = App.getApp();
    }

    private String readFixture(String filename) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(getClass()
                .getClassLoader().getResource(filename).toURI())), StandardCharsets.UTF_8);
    }

    @Test
    public final void testCheckUrlHandler() throws IOException, URISyntaxException, SQLException {
        mockServer = new MockWebServer();
        MockResponse mockedResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(readFixture("index.html"));
        mockServer.enqueue(mockedResponse);
        mockServer.start();

        String url = mockServer.url("/").toString().replaceAll("/$", "");
        Url urlEntity = new Url(url);
        UrlRepository.save(urlEntity);
        Long id = urlEntity.getId();

        JavalinTest.test(app, (server, client) -> {
            String requestBody = "url=" + url;

            var response = client.post("/urls/" + id + "/checks", requestBody); // Новый путь

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains(url);

            List<UrlCheck> checkResult = UrlCheckRepository.getEntitiesForThisUrl(id);
            assertThat(checkResult).isNotEmpty();

            UrlCheck urlCheck = checkResult.get(0);
            assertThat(urlCheck.getStatusCode()).isEqualTo(200);
            assertThat(urlCheck.getTitle()).isEqualTo("Test Page");
            assertThat(urlCheck.getH1()).isEqualTo("Welcome to the Test Page");
            assertThat(urlCheck.getDescription())
                    .isEqualTo("This is a test page for checking the URL handler.");
            assertThat(urlCheck.getUrlId()).isEqualTo(id);
        });

        mockServer.shutdown();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testShow() throws SQLException {

        String testUrl = "https://example.com";
        Url urlEntity = new Url(testUrl);
        UrlRepository.save(urlEntity);
        Long id = urlEntity.getId();

        JavalinTest.test(app, (server, client) -> {

            var response = client.get("/urls");

            assertThat(response.code()).isEqualTo(200);

            String responseBody = response.body().string();
            assertThat(responseBody).contains(testUrl);
            assertThat(responseBody).contains("/urls/" + id);
        });
    }

    @Test
    public void testGetUrlById() throws SQLException {
        var testUrl = "https://www.example.com";
        var url = new Url(testUrl);
        UrlRepository.save(url);
        Long id = url.getId();

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + id);

            assertThat(response.code()).isEqualTo(200);

            String responseBody = response.body().string();
            assertThat(responseBody).contains(testUrl);
        });
    }

    @Test
    public void testAddUrlHandlerWithInvalidUrl() {
        String invalidUrl = "invalid-url";

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + invalidUrl;

            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);

            List<Url> savedUrls = UrlRepository.getEntities();
            assertThat(savedUrls).noneMatch(url -> url.getName().equals(invalidUrl));
        });
    }

    @Test
    public void testAddUrlHandlerWithExistingUrl() throws SQLException {
        var existingUrl = new Url("https://www.example.com");
        UrlRepository.save(existingUrl);

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.example.com";
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);

            List<Url> savedUrls = UrlRepository.getEntities();
            assertThat(savedUrls)
                    .filteredOn(url -> url.getName().equals("https://www.example.com"))
                    .hasSize(1);
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testAddUrlHandler() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com";
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://example.com");
            Optional<Url> result = UrlRepository.findByName("https://example.com");
            assertThat(result).isPresent();
        });
    }
}
