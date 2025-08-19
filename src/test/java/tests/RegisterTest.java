package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class RegisterTest extends TestBase {

    @Test
    void successfulRegistrationTest() {
        String registrationData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";

        given()
                .body(registrationData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", not(isEmptyOrNullString()));
    }

    @Test
    void unsuccessfulRegistrationWithoutPasswordTest() {
        String registrationData = "{\"email\":\"eve.holt@reqres.in\"}";
        given()
                .body(registrationData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unsuccessfulRegistrationWithIncorrectEmailTest() {
        String registrationData = "{\"email\":\"holt@reqres.in\",\"password\":\"pistol\"}";
        given()
                .body(registrationData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void unsuccessfulRegistrationWithoutEmailAndPasswordTest() {
        String registrationData = "{}";
        given()
                .body(registrationData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}