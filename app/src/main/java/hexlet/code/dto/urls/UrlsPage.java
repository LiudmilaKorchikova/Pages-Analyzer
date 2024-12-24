package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
@Setter
public class UrlsPage extends BasePage {
    private List<Url> urls;
    private String flash;
    private Map<Long, UrlCheck> lastChecks;
}
