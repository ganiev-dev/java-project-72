package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.dto.IndexPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.resolve.ResourceCodeResolver;
import lombok.extern.slf4j.Slf4j;

import static io.javalin.rendering.template.TemplateUtil.model;

@Slf4j
public class App {

    public static Javalin getApp() throws Exception {
        //Conn to DB and init
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl());
        var dataSource = new HikariDataSource(hikariConfig);
        var sql = readResourceFile("schema.sql");
        log.info(sql);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> {
            ctx.render("index.jte");
            var header = "Главная";
            var page = new IndexPage(header);
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("index.jte", model("page", page));
        });

        app.post("/urls", ctx -> {
            var inputUrl = ctx.formParam("url");
            try {
                var url = urlNormalizer(inputUrl);
                if (UrlRepository.existsByName(url)) {
                    ctx.sessionAttribute("flash", "Страница уже существует");
                    log.info("URL existed: {}", url);
                } else {
                    var entity = new Url(url);
                    UrlRepository.save(entity);
                    ctx.sessionAttribute("flash", "Страница добавлена успешно");
                    log.info("URL successfully added: {}", url);
                }

            } catch (URISyntaxException | MalformedURLException e) {
                ctx.sessionAttribute("flash", "Некорректный URL");
                log.info("Invalid URL format: {}", inputUrl, e);
                ctx.redirect("/");
                return;
            }

            ctx.redirect("/urls");
        });
        app.get("/health", ctx -> ctx.result("Ok"));

        app.get("/urls", ctx -> {
            var urls = UrlRepository.getEntities();
            var header = "Добавленные урл";
            var page = new UrlsPage(urls, header);
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("urls.jte", model("page", page));
        });

        app.get("/urls/{id}", ctx -> {

            var id = ctx.pathParamAsClass("id", Long.class).get();
            var header = "Url " + id;
            var url = UrlRepository.find(id) // Ищем пользователя в базе по id
                    .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

            var page = new UrlPage(url, header);
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("url.jte", model("page", page));
        });

        return app;








    }

    public static void main(String[] args) throws Exception {
        Javalin app = App.getApp();
        app.start(getPort());
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static String getDatabaseUrl() {
        // Получаем url базы данных из переменной окружения DATABASE_URL
        // Если она не установлена, используем базу в памяти
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project");
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }

    private static String urlNormalizer(String inputUrl) throws URISyntaxException, MalformedURLException {
        if (!inputUrl.startsWith("http://") && !inputUrl.startsWith("https://")) {
            throw new MalformedURLException();
        }

        URI uri = new URI(inputUrl);
        URL parsedUrl = uri.toURL();

        String protocol = parsedUrl.getProtocol();
        String host = parsedUrl.getHost();
        int port = parsedUrl.getPort();

        StringBuilder normalizedUrl = new StringBuilder();
        normalizedUrl.append(protocol).append("://").append(host);
        if (port != -1) {
            normalizedUrl.append(":").append(port);
        }

        return normalizedUrl.toString();
    }




}
