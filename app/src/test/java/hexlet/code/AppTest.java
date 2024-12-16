package hexlet.code;

import hexlet.code.controller.UrlController;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.testtools.JavalinTest;
import io.javalin.validation.Validator;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppTest {

    Javalin app;

    @BeforeEach
    public final void setUp() throws Exception {
        app = App.getApp();
    }

    @Test
    public final void testAdd() throws IOException, InterruptedException, SQLException {
        Url url = new Url("https://example.com");
        UrlRepository.save(url);

        // Создаем и настраиваем мок-сервер
        MockWebServer mockServer = new MockWebServer();

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "text/html")
                .addHeader("Date", "Mon, 16 Dec 2024 12:30:00 GMT")
                .setBody("{\"message\": \"This is a test response\"}")
                .setResponseCode(200);

        mockServer.enqueue(mockResponse);
        mockServer.start();

        String baseUrl = mockServer.url("/").toString();

        Unirest.config().defaultBaseUrl(baseUrl);

        Context ctx = mock(Context.class);


        when(ctx.formParam(anyString())).thenReturn("https://example.com");
        UrlController.addUrlHandler(ctx);

        verify(ctx).redirect(NamedRoutes.urlsPath());


    }

    @Test
    public final void showTest() throws IOException, SQLException {
        Url url = new Url("https://example.com");
        UrlRepository.save(url);

        MockWebServer mockServer = new MockWebServer();

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "text/html")
                .addHeader("Date", "Mon, 16 Dec 2024 12:30:00 GMT")
                .setBody("{\"message\": \"This is a test response\"}")
                .setResponseCode(200);

        mockServer.enqueue(mockResponse);
        mockServer.start();

        String baseUrl = mockServer.url("/").toString();

        Unirest.config().defaultBaseUrl(baseUrl);

        Context ctx = mock(Context.class);


   
        Validator<Long> validatorMock = mock(Validator.class);
        when(ctx.pathParamAsClass("id", Long.class)).thenReturn(validatorMock);

        when(validatorMock.get()).thenReturn(1L);
        UrlController.show(ctx);

        verify(ctx).render(anyString(), anyMap());

        HttpResponse<String> response = Unirest.get("/").asString();
        assert (response.getStatus() == 200);
    }


    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testAddUrl() throws SQLException {
        var url = new Url("https://www.example.com");
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
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
