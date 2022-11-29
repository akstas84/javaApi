package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class cookiesTests {

    @Test
    void cookiesTest(){
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        String strCookie = response.cookie("HomeWork");
        assertEquals(strCookie, "hw_value", "Unexpected cookie value: " + strCookie);
    }

}
