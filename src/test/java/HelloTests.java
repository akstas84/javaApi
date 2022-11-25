import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloTests {

    @ParameterizedTest
    @ValueSource(strings = {"","John","Pate"})
    void helloMethodWithoutNameTest(String name){
        Map<String, String> map = new HashMap<>();
                if(name.length() > 0) {
            map.put("name", name);
        }
        JsonPath response = RestAssured
                .given()
                .queryParams(map)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        String answer = response.getString("answer");
        String expectedName = (name.length() > 0)? name : "someone";
        assertEquals("Hello, " + expectedName, answer, "This answer is not expected");
    }

    @Test
    public void getTextTest(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_text")
                .andReturn();
        response.prettyPrint();
        assertEquals(200, response.statusCode(), "Unexpected status code");
    }
}
