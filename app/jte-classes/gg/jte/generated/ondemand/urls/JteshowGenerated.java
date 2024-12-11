package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlPage;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,4,4,7,7,13,13,13,17,17,17,21,21,21,25,25,25,30,30,30,30,30,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <div>\r\n        <table class=\"table\">\r\n            <tbody>\r\n            <tr>\r\n                <td>Сайт:</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            <tr>\r\n                <td>ID</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            <tr>\r\n                <td>Имя</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            <tr>\r\n                <td>Дата создания</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(String.valueOf(page.getUrl().getCreatedAt()));
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            </tbody>\r\n        </table>\r\n    </div>\r\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
