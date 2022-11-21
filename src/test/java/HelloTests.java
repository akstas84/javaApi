import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class HelloTests {

    @Test
    public void getTextTest(){

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        int statusCode = response.getStatusCode();
        String headerLocation = response.getHeader("Location");
        System.out.println(headerLocation);

        while(statusCode != 200 && headerLocation!=null){
            Response responseUni = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(headerLocation)
                    .andReturn();
            headerLocation = responseUni.getHeader("Location");
            if(headerLocation!=null)
                System.out.println(headerLocation);
            statusCode = responseUni.getStatusCode();
        }
    }
}
