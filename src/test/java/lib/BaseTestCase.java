package lib;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.hasKey;

public class BaseTestCase {
    protected String getHeader(Response response, String name){
        Headers headers = response.getHeaders();
        assertTrue(headers.hasHeaderWithName(name), "Response doesn't have header with name: " + name);
        return headers.getValue(name);
    }
    protected String getCookie(Response response, String name){
        Map<String, String> cookie = response.getCookies();
        assertTrue(cookie.containsKey(name), "Response doesn't have cookie with name: " + name);
        return cookie.get(name);
    }
    protected int getIntFromJson(Response response, String name){
        response.then().assertThat().body("$", hasKey(name));
        return response.jsonPath().getInt(name);
    }
}
