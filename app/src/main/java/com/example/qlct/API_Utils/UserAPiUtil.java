package com.example.qlct.API_Utils;

import android.util.Log;

import com.example.qlct.API_Config;
import com.example.qlct.API_Entity.CreateRenewBudgetEntity;
import com.example.qlct.API_Entity.CreateUserRegiterEntity;
import com.example.qlct.API_Entity.LoginEntity;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserAPiUtil {
    private String SERVER = API_Config.SERVER;
    private String API_VERSION = API_Config.API_VERSION;
    private String LOGIN_TOKEN = API_Config.TEST_LOGIN_TOKEN;
    public void RegisterUser(CreateUserRegiterEntity createUserRegiterEntity) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create( new Gson().toJson(createUserRegiterEntity), mediaType);
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/user/register")
                    .method("POST", body)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Register", jsonData);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String Login (LoginEntity loginEntity) {
        String jsonData="";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create( new Gson().toJson(loginEntity), mediaType);
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/user/login")
                    .method("POST", body)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            jsonData = response.body().string();
            Log.d("Login", jsonData);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
