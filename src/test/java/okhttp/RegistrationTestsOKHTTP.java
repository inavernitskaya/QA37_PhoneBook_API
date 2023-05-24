package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTestsOKHTTP {
    Gson gson=new Gson();
    public static final MediaType JSON=MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("anni_27@gmail.com").password("Qq13579$").build();
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
        Assert.assertEquals(errorDTO.getStatus(),400);
        Assert.assertEquals(errorDTO.getMessage(),"{username=must be a well-formed email address}");
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/registration/usernamepassword");


    }
    @Test
    public void registrationWrongPassword() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("cwon@gmail.com").password("Ww13").build();
        RequestBody body= RequestBody.create(gson.toJson(auth),JSON);
        Request request= new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(),400);
        Assert.assertEquals(errorDTO.getMessage(),"{password= At least 8 characters; " +
                "Must contain at least 1 uppercase letter, " +
                "1 lowercase letter, " +
                "and 1 number; " +
                "Can contain special characters [@$#^&*!]}");
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/registration/usernamepassword");
    }

    @Test
    public void registrationExistUser() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("Inna_83@gmail.com").password("Aa13579$").build();
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








