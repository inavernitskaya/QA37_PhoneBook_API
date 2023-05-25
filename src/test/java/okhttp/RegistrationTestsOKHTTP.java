package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationTestsOKHTTP {
    Gson gson=new Gson();
    public static final MediaType JSON=MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {
        Random random = new Random();
        int i = random.nextInt(1000) + 1000;
        AuthRequestDTO auth = AuthRequestDTO.builder().username("anni_27"+i+"@gmail.com").password("Qq13579$").build();
        RequestBody body= RequestBody.create(gson.toJson(auth),JSON);
        Request request= new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(),AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }

    @Test
    public void registrationWrongEmail() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("cwongmail.com").password("Ww13579$").build();
        RequestBody body= RequestBody.create(gson.toJson(auth),JSON);
        Request request= new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        //Assert.assertEquals(errorDTO.getStatus(),400);
        Assert.assertTrue(errorDTO.getMessage().toString().contains("must be a well-formed email address"));
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/registration/usernamepassword");


    }
    @Test
    public void registrationWrongPassword() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("kan@gmail.com").password("Ww13").build();
        RequestBody body= RequestBody.create(gson.toJson(auth),JSON);
        Request request= new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
       // Assert.assertEquals(errorDTO.getStatus(),400);
        Assert.assertTrue(errorDTO.getMessage().toString().contains("At least 8 characters; Must contain at least"));
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/registration/usernamepassword");
    }

    @Test
    public void registrationExistUser() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("inna_83@gmail.com").password("Aa13579$").build();
        RequestBody body= RequestBody.create(gson.toJson(auth),JSON);
        Request request= new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),409);

        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(),409);
        Assert.assertEquals(errorDTO.getMessage(),"User already exists");
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/registration/usernamepassword");

    }
}








