package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.javalin.testtools.JavalinTest;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    private Javalin app;

    @BeforeEach
    public final void setUp() throws Exception {
        System.setProperty("JDBC_DATABASE_URL", "jdbc:h2:mem:project");
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Главная страница");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://ru.hexlet.io/";
            var entity = new Url(url);
            UrlRepository.save(entity);

            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains(url);
        });
    }

    @Test
    public void testShowUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://github.com/";
            var entity = new Url(url);
            UrlRepository.save(entity);

            var saved = UrlRepository.getEntities().getFirst();
            var id = saved.getId();

            var response = client.get("/urls/" + id);
            var body = response.body().string();

            assertThat(response.code()).isEqualTo(200);
            assertThat(body).contains("Url " + id).contains(url);
        });
    }
}
