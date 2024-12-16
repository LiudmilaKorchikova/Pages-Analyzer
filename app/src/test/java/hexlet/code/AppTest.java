package hexlet.code;

import hexlet.code.controller.UrlController;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.testtools.JavalinTest;
import io.javalin.validation.Validator;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppTest {

    Javalin app;

    @BeforeEach
    public final void setUp() throws Exception {
        app = App.getApp();
    }

    @Test
    public final void showTest() throws IOException, InterruptedException, SQLException {
        MockWebServer server = new MockWebServer();

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "text/html")
                .addHeader("Date", "Mon, 16 Dec 2024 12:30:00 GMT")
                .setBody("{\"message\": \"This is a test response\"}")
                .setResponseCode(200);

        server.enqueue(mockResponse);

        HttpUrl baseUrl = server.url("http://localhost:7070/urls/1");

        UrlController urlController = mock(UrlController.class);
        UrlRepository urlRepository = mock(UrlRepository.class);
        UrlCheckRepository urlCheckRepository = mock(UrlCheckRepository.class);
        Context contextMock = mock(Context.class);
        Validator<Long> validatorMock = mock(Validator.class);
        when(contextMock.pathParamAsClass("id", Long.class)).thenReturn(validatorMock);

        when(validatorMock.get()).thenReturn(1L);

        Url url = new Url("https://www.example.com", LocalDateTime
                .of(2024, 12, 16, 12, 30));

        urlController.show(contextMock);

        when(urlRepository.findByIndex(1L)).thenReturn(Optional.of(url));
        when(urlCheckRepository.getEntitiesForThisUrl(1L)).thenReturn(new ArrayList<>());

        RecordedRequest request = server.takeRequest();

        assertEquals("GET", request.getMethod());
        assertEquals("/urls/1", request.getRequestUrl().encodedPath());
        assertEquals("text/html", request.getHeader("Content-Type"));

        server.shutdown();
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
