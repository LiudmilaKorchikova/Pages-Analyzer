package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;


import hexlet.code.model.UrlCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> urlChecks;
}
