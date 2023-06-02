package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIDRA {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiaW5uYV84M0BnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NjMxMDI3OCwiaWF0IjoxNjg1NzEwMjc4fQ.jVTMM0QycozH8uzkYqlZzcXgtdfgFIjBTrEMH_pCBUQ";

    String id;

    String contactIdToDelete;

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

        int i = new Random().nextInt(1000) + 1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Donna")
                .lastName("Doww")
                .email("donna" + i + "@gmail.com")
                .phone("12345600" + i)
                .address("Tek Aviv").build();

        String message = given()
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("message");
        String[] all = message.split(": ");
        id = all[1];


    }

    @Test
    public void deleteContactByID() {
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
    }

    @Test
    public void deleteContactByIdWrongToken() {
        given()
                .header("Authorization", "hfhgh")
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(401);
    }

    @Test
    public void deleteContactByIdWrongFormat() {
        given()
                .header("Any format error", token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(400);
    }


     @Test
    public void  deleteContactByIdWrongContact(){
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/" + contactIdToDelete)
                .then()
                .assertThat().statusCode(404);
    }
}