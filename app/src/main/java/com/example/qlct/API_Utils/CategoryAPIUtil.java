package com.example.qlct.API_Utils;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlct.API_Config;
import com.example.qlct.API_Entity.CreateCategoryEntity;
import com.example.qlct.API_Entity.GetAllCategoryEntity;
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

public class CategoryAPIUtil extends AppCompatActivity {
    private String SERVER = API_Config.SERVER;
    private String API_VERSION = API_Config.API_VERSION;
    private String LOGIN_TOKEN = API_Config.TEST_LOGIN_TOKEN;
    public ArrayList<GetAllCategoryEntity> getAllCategory (){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Log.d("url", SERVER + "/" + API_VERSION + "/category");
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/category")
                    .method("GET", null)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Get_category", jsonData);
            JSONObject json = new JSONObject(jsonData);

            //Maping json to entity
            Type listType = new TypeToken<ArrayList<GetAllCategoryEntity>>(){}.getType();
            ArrayList<GetAllCategoryEntity> parseAPIList = new Gson().fromJson(json.getJSONObject("data").getJSONArray("rows").toString(), listType);

            return parseAPIList;
        }
        catch(Exception e) {
            //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
            e.printStackTrace();
            return null;
        }
    }
    public void createCategoryAPI( CreateCategoryEntity createCategoryEntity) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create( new Gson().toJson(createCategoryEntity), mediaType);
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/category")
                    .method("POST", body)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Create_wallet", jsonData);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteCategory(String categoryId){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/category/" + categoryId)
                    .method("DELETE", null)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Delete_wallet", jsonData);
        }catch (Exception e) {
            e.printStackTrace();
        } }
}
