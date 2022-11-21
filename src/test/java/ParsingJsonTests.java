import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class ParsingJsonTests {

    @Test
    void parsingJsonTest(){
        JsonPath response = RestAssured
                .given()
                .get(" https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        System.out.println("\nSecond Timestamp");
        String answer = response.get("messages[1].timestamp");
        System.out.println(answer);
    }
}
