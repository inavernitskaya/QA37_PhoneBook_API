package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ErrorDTO;
import dto.GetAllContactsDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsTestsOKHTTP {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiaW5uYV84M0BnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NTM3NDY5OSwiaWF0IjoxNjg0Nzc0Njk5fQ.9jg97pn_pNa-WIohGpFS1Zu_atIrUvTl0nLzhafiAnw";
    Gson gson=new Gson();
    OkHttpClient client = new OkHttpClient();
    @Test
    public void GetAllContactSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization",token)
                .build();
        Response response=client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        GetAllContactsDTO contactsDTO = gson.fromJson(response.body().string(), GetAllContactsDTO.class);
        List<ContactDTO> contacts= contactsDTO.getContacts();
        for (ContactDTO c:contacts){
            System.out.println(c.getId());
            System.out.println(c.getEmail());
        }



    }

    @Test
    public void GetAllContactWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization","jhgfr678i")
                .build();
        Response response=client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDTO =gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(),"Unauthorized");
    }
}
