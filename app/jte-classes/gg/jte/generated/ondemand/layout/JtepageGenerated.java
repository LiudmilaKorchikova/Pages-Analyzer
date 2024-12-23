package gg.jte.generated.ondemand.layout;
import com.fasterxml.jackson.databind.util.Named;
import gg.jte.Content;
import hexlet.code.dto.BasePage;
import hexlet.code.utils.NamedRoutes;
public final class JtepageGenerated {
	public static final String JTE_NAME = "layout/page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,5,5,5,30,30,30,30,30,30,30,30,30,30,37,38,40,40,42,42,42,44,44,46,46,46,50,59,59,59,5,6,6,6,6};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, BasePage page) {
		jteOutput.writeContent("\r\n<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n    <meta name=\"description\" content=\"Используйте этот сайт для проверки ваших сайтов на SEO пригодность.\">\r\n    <title>Pages Analyzer</title>\r\n    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n</head>\r\n<body>\r\n<nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\r\n    <div class=\"container-fluid\">\r\n        <a class=\"navbar-brand\" href=\"/\">Анализатор страниц</a>\r\n        <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNav\" aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n            <span class=\"navbar-toggler-icon\"></span>\r\n        </button>\r\n        <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\r\n            <ul class=\"navbar-nav\">\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link active\" href=\"/\">Главная</a>\r\n                </li>\r\n                <li class=\"nav-item\">\r\n                    <a class=\"nav-link\"");
		var __jte_html_attribute_0 = NamedRoutes.urlsPath();
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" href=\"");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(">Сайты</a>\r\n                </li>\r\n            </ul>\r\n        </div>\r\n    </div>\r\n</nav>\r\n\r\n<div class=\"container my-4\"> ");
		jteOutput.writeContent("\r\n    ");
		jteOutput.writeContent("\r\n\r\n    ");
		if (page != null && page.getFlash() != null) {
			jteOutput.writeContent("\r\n        <div class=\"alert alert-info alert-dismissible fade show mb-4\" role=\"alert\">\r\n            ");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("\r\n        </div>\r\n    ");
		}
		jteOutput.writeContent("\r\n\r\n    ");
		jteOutput.setContext("div", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\r\n\r\n</div>\r\n\r\n");
		jteOutput.writeContent("\r\n<footer class=\"bg-dark text-white text-center p-3 mt-5\">\r\n    <p>Создано для учебных целей.</p>\r\n</footer>\r\n\r\n<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>\r\n<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>\r\n<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\r\n</body>\r\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		BasePage page = (BasePage)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, content, page);
	}
}
