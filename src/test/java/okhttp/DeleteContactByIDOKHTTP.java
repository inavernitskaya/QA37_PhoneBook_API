package okhttp;

import com.google.gson.Gson;
import dto.DeleteByIdResponseDTO;
import dto.ErrorDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactByIDOKHTTP {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiaW5uYV84M0BnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NTM3NDY5OSwiaWF0IjoxNjg0Nzc0Njk5fQ.9jg97pn_pNa-WIohGpFS1Zu_atIrUvTl0nLzhafiAnw";
    Gson gson=new Gson();
    OkHttpClient client = new OkHttpClient();
    String id;


    @BeforeMethod
    public void precondition(){
        // create contact
        // get id from "message": "Contact was added! ID: 932c375d-1fb4-4255-be43-76ef37dabeec"
        // id="".
    }
    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/e19db416-b267-4762-9c82-55a6e28abe2b")
                .delete()
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),200);
        DeleteByIdResponseDTO dto = gson.fromJson(response.body().string(), DeleteByIdResponseDTO.class);
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