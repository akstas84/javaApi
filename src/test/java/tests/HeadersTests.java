package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeadersTests {

    @Test
    void headerValueTest(){
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .jsonPath();
        String answer = response.get("success");
        assertEquals(answer, "!", "Unexpected header value:" + answer);
    }

}
