package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "urlChecks")
public final class Url {

    private Long id;

    private String name;
    private LocalDateTime createdAt;

    private List<UrlCheck> urlChecks;

    public Url() { }

    public Url(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public UrlCheck getLastUrlCheck() {
        return urlChecks.get(urlChecks.size() - 1);
    }

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return createdAt.format(formatter);
    }
}
