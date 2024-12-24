package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
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

    private UrlCheck lastUrlCheck;

    public Url() { }

    public Url(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
