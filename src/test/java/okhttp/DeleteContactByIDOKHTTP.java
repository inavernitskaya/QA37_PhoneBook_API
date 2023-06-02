package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.MessageDTO;
import dto.ErrorDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

import static okhttp.RegistrationTestsOKHTTP.JSON;

public class DeleteContactByIDOKHTTP {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiaW5uYV84M0BnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NTM3NDY5OSwiaWF0IjoxNjg0Nzc0Njk5fQ.9jg97pn_pNa-WIohGpFS1Zu_atIrUvTl0nLzhafiAnw";
    Gson gson=new Gson();
    OkHttpClient client = new OkHttpClient();
    String id;


    @BeforeMethod
    public void precondition() throws IOException {
        // create contact
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Maya")
                .lastName("Dow")
                .address("NY")
                .email("maya"+i+"@gmail.com")
                .phone("1234556"+i)
                .description("The best friend")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDTO),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        MessageDTO messageDTO = gson.fromJson(response.body().string(),MessageDTO.class);
        String  message = messageDTO.getMessage(); //"Contact was added! ID: 932c375d-1fb4-4255-be43-76ef37dabeec"

        System.out.println( message);
        // get id from "message": "Contact was added! ID: 932c375d-1fb4-4255-be43-76ef37dabeec"
        String[] all = message.split(": ");
        // id="".
        id = all[1];
        System.out.println(id);
    }
    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)
                .delete()
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),200);
        MessageDTO dto = gson.fromJson(response.body().string(), MessageDTO.class);
        Assert.assertEquals(dto.getMessage(),"Contact was deleted!");
        System.out.println( dto.getMessage());

    }

    @Test
    public void deleteContactByIdWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/d35dcb44-2498-4936-b9e8-482b87063a6e")
                .delete()
                .addHeader("Authorization","gfhf")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(),"Unauthorized");


    }
    @Test
    public void deleteContactByIdNotFound() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+123)
                .delete()
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),400);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(),"Bad Request");
        Assert.assertEquals(errorDTO.getMessage(),"Contact with id: 123 not found in your contacts!");


    }


}



//e19db416-b267-4762-9c82-55a6e28abe2b
//hgfd@gmail.com
//3e85c91a-ee62-4bf0-b364-d4a5770f557b
//jnbchg@gmail.com
//101b0c1f-7093-4b78-8c19-cbf01ad6d728
//pomn@gmail.com