package gg.jte.generated.ondemand;
import hexlet.code.dto.UrlsPage;
public final class JteurlsGenerated {
	public static final String JTE_NAME = "urls.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,13,13,13,18,18,23,23,25,25,25,25,25,25,25,27,27,34,34,34,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n    <meta charset=\"UTF-8\">\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n    <title>Список URL</title>\n</head>\n<body>\n<div class=\"container\">\n    <h1>Список добавленных URL</h1>\n        ");
		if (page.getUrl().isEmpty()) {
			jteOutput.writeContent("\n            <div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">\n            <p>Пока не добавлено ни одного URL</p>\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n            </div>\n        ");
		}
		jteOutput.writeContent("\n\n\n\n        <ul>\n            ");
		for (var url : page.getUrl()) {
			jteOutput.writeContent("\n                <li>\n                    <a href=\"/urls/");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(url.getId());
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\">");
			jteOutput.setContext("a", null);
			jteOutput.writeUserContent(url.getName());
			jteOutput.writeContent("</a>\n                </li>\n            ");
		}
		jteOutput.writeContent("\n        </ul>\n\n    <a href=\"/\">На главную</a>\n    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n</div>>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
