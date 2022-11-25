import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAuthTests {

    String cookie;
    String header;
    int userIdOnAuth;

    @BeforeEach
    public void loginUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        cookie = responseGetAuth.getCookie("auth_sid");
        header = responseGetAuth.getHeader("x-csrf-token");
        userIdOnAuth = responseGetAuth.jsonPath().getInt("user_id");

        //assertEquals(200, responseGetAuth.getStatusCode(), "Unexpected status code");
        //assertTrue(responseGetAuth.jsonPath().getInt("user_id") > 0, "User id should be greater than 0");

    }

    @Test
    void userAuthTest() {
        JsonPath responseCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", this.cookie)
                .cookies("auth_sid", this.header)
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();
        int userIdOnCheck = responseCheckAuth.getInt("user_id");
        assertTrue(userIdOnCheck > 0, "Unexpected user id " + userIdOnCheck);
        assertEquals(userIdOnAuth, userIdOnCheck, "user_id from auth request is not equal to user_id from check request");
    }

    @ParameterizedTest
    @ValueSource(strings={"cookie", "headers"})
    void userAuthNegativeTest(String conditions) {

        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if(conditions.equals("cookie")) {
            spec.cookie("auth_sid", this.cookie);
        } else if(conditions.equals("headers")) {
            spec.header("x-csrf-token", this.header);
        } else {
            throw new IllegalArgumentException("Condition value is know: " + conditions);
        }
        JsonPath responseForCheck = spec.get().jsonPath();
        assertEquals(0, responseForCheck.getInt("user_id"), "user_id should be 0 for unauth request");
    }
}
