import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HelloTests {

    @Test
    public void tokenTest() throws InterruptedException {
        JsonPath jsonPath = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String token = jsonPath.getString("token");
        int sec = Integer.parseInt(jsonPath.getString("seconds"));
        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        RestAssured
                .given()
                .queryParams(map)
                .when().get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .then().assertThat().statusCode(200)
                .and()
                .body("status", is("Job is NOT ready"))
                .extract().response();

        TimeUnit.SECONDS.sleep(sec);
        RestAssured
                .given()
                .queryParams(map)
                .when().get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .then().assertThat().statusCode(200)
                .and()
                .body("status", is("Job is ready"))
                .extract().response();
    }
}
