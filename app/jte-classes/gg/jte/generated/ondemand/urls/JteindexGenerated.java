package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.utils.NamedRoutes;
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,5,5,8,8,22,22,25,25,25,26,26,26,26,26,26,26,26,26,26,26,26,27,27,27,28,28,29,29,29,30,30,30,31,31,34,34,38,38,38,38,38,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <h1>Список Url</h1>\r\n    <div>\r\n        <table class=\"table table-striped table-bordered table-hover\">\r\n            <thead>\r\n            <tr>\r\n                <th>ID</th>\r\n                <th>URL</th>\r\n                <th>Дата создания</th>\r\n                <th>Последняя проверка</th>\r\n                <th>Код ответа</th>\r\n            </tr>\r\n            </thead>\r\n            <tbody>\r\n            ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\r\n\r\n                <tr>\r\n                    <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("</td>\r\n                    <td><a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a></td>\r\n                    <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getFormattedCreatedAt());
					jteOutput.writeContent("</td>\r\n                    ");
					if (!url.getUrlChecks().isEmpty() && url.getUrlChecks() != null) {
						jteOutput.writeContent("\r\n                        <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getLastUrlCheck().getFormattedCreatedAt());
						jteOutput.writeContent("</td>\r\n                        <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getLastUrlCheck().getStatusCode());
						jteOutput.writeContent("</td>\r\n                    ");
					}
					jteOutput.writeContent("\r\n                </tr>\r\n\r\n            ");
				}
				jteOutput.writeContent("\r\n            </tbody>\r\n        </table>\r\n    </div>\r\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
