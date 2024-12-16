package hexlet.code.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class UrlCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int statusCode;
    private String title;
    private String h1;

    @Lob
    private String description;  // Для хранения больших текстов

    private Long urlId;

    private LocalDateTime createdAt;

    public UrlCheck() { }

    public UrlCheck(Long urlId, int statusCode, String title, String h1, String description, LocalDateTime createdAt) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
        this.createdAt = createdAt;
    }

    public UrlCheck(Long urlId, int statusCode, String title, String h1, LocalDateTime createdAt) {
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.urlId = urlId;
        this.createdAt = createdAt;
    }

    public UrlCheck(Long urlId, int statusCode, LocalDateTime createdAt) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.createdAt = createdAt;
    }

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return createdAt.format(formatter);
    }
}
