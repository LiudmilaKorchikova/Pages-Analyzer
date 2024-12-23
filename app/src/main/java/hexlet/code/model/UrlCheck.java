package hexlet.code.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public final class UrlCheck {

    private Long id;

    private int statusCode;
    private String title;
    private String h1;

    private String description;

    private Long urlId;

    private LocalDateTime createdAt;

    public UrlCheck(Long urlId, int statusCode, String title, String h1, String description) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public UrlCheck(Long urlId, int statusCode, String title, String h1) {
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.urlId = urlId;
        this.createdAt = LocalDateTime.now();
    }

    public UrlCheck(Long urlId, int statusCode) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.createdAt = LocalDateTime.now();
    }

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return createdAt.format(formatter);
    }
}
