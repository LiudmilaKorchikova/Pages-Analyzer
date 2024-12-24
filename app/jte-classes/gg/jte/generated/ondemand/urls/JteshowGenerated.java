package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.utils.NamedRoutes;
import hexlet.code.utils.DateFormatter;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,6,6,9,9,11,11,11,18,18,18,22,22,22,26,26,26,32,32,32,32,32,32,32,32,32,37,37,51,51,53,53,53,54,54,54,56,56,57,57,57,58,58,59,59,59,60,60,61,61,61,62,62,64,64,64,65,65,65,66,66,66,68,68,71,71,73,73,73,74,74,74,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <div>\r\n        <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\r\n    </div>\r\n    <div>\r\n        <table class=\"table table-striped table-bordered\">\r\n            <tbody>\r\n            <tr>\r\n                <td><strong>ID</strong></td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            <tr>\r\n                <td><strong>Имя</strong></td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            <tr>\r\n                <td><strong>Дата создания</strong></td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(DateFormatter.format(page.getUrl().getCreatedAt()));
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            </tbody>\r\n        </table>\r\n    </div>\r\n    <div>\r\n        <form");
				var __jte_html_attribute_0 = NamedRoutes.urlChecksPath(page.getUrl().getId());
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" method=\"POST\">\r\n            <button type=\"submit\" class=\"btn btn-primary\">Проверить</button>\r\n        </form>\r\n    </div>\r\n    <div>\r\n        ");
				if (!page.getUrlChecks().isEmpty()) {
					jteOutput.writeContent("\r\n            <h3>Результаты проверок:</h3>\r\n            <table class=\"table table-striped table-bordered\">\r\n                <thead>\r\n                <tr>\r\n                    <th>ID</th>\r\n                    <th>Дата проверки</th>\r\n                    <th>Статус-код</th>\r\n                    <th>Title</th>\r\n                    <th>H1</th>\r\n                    <th>Description</th>\r\n                </tr>\r\n                </thead>\r\n                <tbody>\r\n                ");
					for (var check : page.getUrlChecks()) {
						jteOutput.writeContent("\r\n                    <tr>\r\n                        <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getId());
						jteOutput.writeContent("</td>\r\n                        <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(DateFormatter.format(check.getCreatedAt()));
						jteOutput.writeContent("</td>\r\n                        <td>\r\n                            ");
						if (check.getStatusCode() == 200) {
							jteOutput.writeContent("\r\n                                <span class=\"badge badge-success\">");
							jteOutput.setContext("span", null);
							jteOutput.writeUserContent(check.getStatusCode());
							jteOutput.writeContent("</span>\r\n                            ");
						} else if (check.getStatusCode() >= 400) {
							jteOutput.writeContent("\r\n                                <span class=\"badge badge-danger\">");
							jteOutput.setContext("span", null);
							jteOutput.writeUserContent(check.getStatusCode());
							jteOutput.writeContent("</span>\r\n                            ");
						} else {
							jteOutput.writeContent("\r\n                                <span class=\"badge badge-warning\">");
							jteOutput.setContext("span", null);
							jteOutput.writeUserContent(check.getStatusCode());
							jteOutput.writeContent("</span>\r\n                            ");
						}
						jteOutput.writeContent("\r\n                        </td>\r\n                        <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getTitle() != null ? check.getTitle() : "N/A");
						jteOutput.writeContent("</td>\r\n                        <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getH1() != null ? check.getH1() : "N/A");
						jteOutput.writeContent("</td>\r\n                        <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getDescription() != null ? check.getDescription() : "N/A");
						jteOutput.writeContent("</td>\r\n                    </tr>\r\n                ");
					}
					jteOutput.writeContent("\r\n                </tbody>\r\n            </table>\r\n        ");
				}
				jteOutput.writeContent("\r\n    </div>\r\n");
			}
		}, page);
		jteOutput.writeContent("\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
