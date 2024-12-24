package hexlet.code.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Url {

    private Long id;

    private String name;
    private LocalDateTime createdAt;

    public Url() { }

    public Url(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
