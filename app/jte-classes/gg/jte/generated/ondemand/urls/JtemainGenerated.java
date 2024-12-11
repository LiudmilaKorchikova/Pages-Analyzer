package gg.jte.generated.ondemand.urls;
import hexlet.code.utils.NamedRoutes;
import hexlet.code.dto.BasePage;
import gg.jte.Content;
public final class JtemainGenerated {
	public static final String JTE_NAME = "urls/main.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,3,3,6,6,8,8,10,12,12,12,12,12,12,12,12,12,19,19,19,19,19,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, BasePage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n\r\n        ");
				jteOutput.writeContent("\r\n        <h2>Добавить URL</h2>\r\n        <form");
				var __jte_html_attribute_0 = NamedRoutes.urlsPath();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" method=\"POST\">\r\n            <div class=\"form-group\">\r\n                <label for=\"url\">Введите URL</label>\r\n                <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\" placeholder=\"Введите URL\" required>\r\n            </div>\r\n            <button type=\"submit\" class=\"btn btn-primary\">Добавить</button>\r\n        </form>\r\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		BasePage page = (BasePage)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, content, page);
	}
}
