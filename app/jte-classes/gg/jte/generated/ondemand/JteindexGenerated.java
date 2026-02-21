package gg.jte.generated.ondemand;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,14,14,14,16,16,16,19,19,28,28,28,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, hexlet.code.dto.IndexPage page) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\" xmlns=\"http://www.w3.org/1999/html\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Главная страница</title>\n\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n</head>\n<body>\n\n<div class=\"container\">\n");
		if (page != null && page.getFlash() != null) {
			jteOutput.writeContent("\n    <div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\n        ");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("\n        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n    </div>\n");
		}
		jteOutput.writeContent("\n    <form action=\"/urls\" method=\"post\">\n        <input class=\"form-control\" type=\"text\" name=\"url\" placeholder=\"Адрес сайта\">\n      <input type=\"submit\" value=\"Добавить в базу\" />\n    </form>\n</div>\n\n<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n</body>\n</html>>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		hexlet.code.dto.IndexPage page = (hexlet.code.dto.IndexPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
