package com.example.qlct.API_Utils;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlct.API_Config;
import com.example.qlct.API_Entity.CreateNoRenewBudgetEntity;
import com.example.qlct.API_Entity.CreateRenewBudgetEntity;
import com.example.qlct.API_Entity.GetAllBudget;
import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BudgetAPIUtil extends AppCompatActivity {
    private String SERVER = API_Config.SERVER;
    private String API_VERSION = API_Config.API_VERSION;
    private String LOGIN_TOKEN = API_Config.TEST_LOGIN_TOKEN;
    public void createRenewBudget(CreateRenewBudgetEntity createRenewBudgetEntity) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create( new Gson().toJson(createRenewBudgetEntity), mediaType);
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/budget/create/renew")
                    .method("POST", body)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Create_budget", jsonData);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createNoRenewBudget(CreateNoRenewBudgetEntity createRenewBudgetEntity) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create( new Gson().toJson(createRenewBudgetEntity), mediaType);
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/budget/create/no-renew")
                    .method("POST", body)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Create_budget", jsonData);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<GetAllBudget> getAllBudgets (){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Log.d("url", SERVER + "/" + API_VERSION + "/budget/list");
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/budget/list")
                    .method("GET", null)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Get_budget", jsonData);
            JSONObject json = new JSONObject(jsonData);

            //Maping json to entity
            Type listType = new TypeToken<ArrayList<GetAllBudget>>(){}.getType();
            ArrayList<GetAllBudget> parseAPIList = new Gson().fromJson(json.getJSONObject("data").getJSONArray("rows").toString(), listType);
            return parseAPIList;
        }
        catch(Exception e) {
            //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
            e.printStackTrace();
            return null;
        }
    }
}
