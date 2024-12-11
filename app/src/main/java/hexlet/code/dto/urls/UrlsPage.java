package hexlet.code.dto.urls;

import java.util.List;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlsPage extends BasePage {
    private List<Url> urls;

    public UrlsPage(List<Url> urls, String flash) {
        super();
        setFlash(flash);
        this.urls = urls;
    }
}
