package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class RegistrationTestsRA {
    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void registrationSuccess() {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;
        AuthRequestDTO auth = AuthRequestDTO.builder().username("annita_27" + i + "@gmail.com").password("Qq13579$").build();
        String token = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("token");
        System.out.println(token);

    }

    @Test
    public void registrationWrongEmail() {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("annita_27gmail.com").password("Qq13579$").build();
        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.username", containsString("must be a well-formed email address"));

    }

    @Test
    public void registrationWrongPassword() {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("annita_27@gmail.com").password("Qq13").build();
        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.password", containsString("At least 8 characters; Must contain at least"));

    }


    @Test
    public void registrationDuplicate() {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("inna_83@gmail.com").password("Aa13579$").build();
        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(409)
                .assertThat().body("message", containsString("User already exists"));

    }

    @Test
    public void registrationAllFieldsUncorrect() {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("annita_27gmail.com").password("Qq13").build();
        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.password", containsString("At least 8 characters; Must contain at least"))
                .assertThat().body("message.username", containsString("must be a well-formed email address"));
    }

}





