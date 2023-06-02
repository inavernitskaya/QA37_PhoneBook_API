package restassured;

import com.jayway.restassured.RestAssured;
import dto.ContactDTO;
import dto.ErrorDTO;
import dto.GetAllContactsDTO;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class GetAllContactsTestsRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiaW5uYV84M0BnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NjMxMDI3OCwiaWF0IjoxNjg1NzEwMjc4fQ.jVTMM0QycozH8uzkYqlZzcXgtdfgFIjBTrEMH_pCBUQ";


        @BeforeMethod
        public void  preCondition(){
            RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
            RestAssured.basePath = "v1";

        }

        @Test
        public void getAllContactsSuccess(){

            GetAllContactsDTO contactsDTO =given()
                    .header("Authorization",token)
                    .when()
                    .get("contacts")
                    .then()
                    .assertThat().statusCode(200)
                    .extract()
                    .response()
                    .as(GetAllContactsDTO.class);


            List<ContactDTO> list =contactsDTO.getContacts();
            for (ContactDTO contact:list) {
                System.out.println(contact.getId());
                System.out.println(contact.getEmail());
                System.out.println("Size of list -->" +list.size());

            }

        }


    @Test
    public void GetAllContactWrongToken() {
        GetAllContactsDTO contactsDTO = given()
                .header("Authorization", "Bearer eyJhbGciOiJW5uYV84M0BnbWFpbC5")
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(401)
                .extract()
                .response()
                .as(GetAllContactsDTO.class);

           }
    }




