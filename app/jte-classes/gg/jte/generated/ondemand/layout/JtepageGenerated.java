package gg.jte.generated.ondemand.layout;
import gg.jte.Content;
import hexlet.code.dto.BasePage;
public final class JtepageGenerated {
	public static final String JTE_NAME = "layout/page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,16,16,18,18,19,19,19,20,20,21,21,21,23,32,32,32,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, BasePage page) {
		jteOutput.writeContent("\r\n<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n    <title>My Website</title>\r\n    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n</head>\r\n<body>\r\n\r\n    ");
		jteOutput.writeContent("\r\n\r\n        ");
		if (page != null && page.getFlash() != null) {
			jteOutput.writeContent("\r\n            <p>");
			jteOutput.setContext("p", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("</p>\r\n        ");
		}
		jteOutput.writeContent("\r\n        ");
		jteOutput.setContext("body", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\r\n\r\n    ");
		jteOutput.writeContent("\r\n    <footer class=\"bg-dark text-white text-center p-3 mt-5\">\r\n        <p>Создано для учебных целей.</p>\r\n    </footer>\r\n\r\n    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>\r\n    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>\r\n    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\r\n</body>\r\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		BasePage page = (BasePage)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, content, page);
	}
}
