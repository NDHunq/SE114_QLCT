package com.example.qlct.API_Utils;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlct.API_Config;

import com.example.qlct.API_Entity.GetAllTransactionsEntity_quyen;

import com.example.qlct.API_Entity.CreateTransactionEntity;
import com.example.qlct.API_Entity.GetAllTransactionsEntity;
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

public class TransactionAPIUtil extends AppCompatActivity {
    private String SERVER = API_Config.SERVER;
    private String API_VERSION = API_Config.API_VERSION;
    /*
    Phía dưới này là token để test, trong thực tế cần lấy token này bằng cách gọi api /login,
     sau đó lưu token vào local storage, và gửi token này trong mỗi request
     */
    private String LOGIN_TOKEN = API_Config.TEST_LOGIN_TOKEN;
//

    public ArrayList<GetAllTransactionsEntity_quyen> getAllTransactionsAPI_quyen (){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Log.d("url", SERVER + "/" + API_VERSION + "/transactions/me");
            Request request = new Request.Builder()

                    .url( SERVER + "/" + API_VERSION + "/transactions/me")
                    .method("GET", null)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Get_transaction", jsonData);
            JSONObject json = new JSONObject(jsonData);

            //Maping json to entity

            Type listType = new TypeToken<ArrayList<GetAllTransactionsEntity_quyen>>(){}.getType();
            ArrayList<GetAllTransactionsEntity_quyen> parseAPIList = new Gson().fromJson(json.getJSONObject("data").getJSONArray("rows").toString(), listType);

            return parseAPIList;
        }
        catch(Exception e) {
            Log.d("123", e.getMessage());

            //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<GetAllTransactionsEntity> getAllTransactionsAPI (){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Log.d("url", SERVER + "/" + API_VERSION + "/transactions/me");
            Request request = new Request.Builder()

                    .url( SERVER + "/" + API_VERSION + "/transactions/me")
                    .method("GET", null)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Get_transaction", jsonData);
            JSONObject json = new JSONObject(jsonData);

            //Maping json to entity

            Type listType = new TypeToken<ArrayList<GetAllTransactionsEntity>>(){}.getType();
            ArrayList<GetAllTransactionsEntity> parseAPIList = new Gson().fromJson(json.getJSONObject("data").getJSONArray("rows").toString(), listType);

            return parseAPIList;
        }
        catch(Exception e) {
            Log.d("123", e.getMessage());

            //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
            e.printStackTrace();
            return null;
        }
    }

    // Hàm gọi API:
    //walletId: id của wallet cần update




    public void createTransactionAPI(CreateTransactionEntity createTransactionEntity){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create( new Gson().toJson(createTransactionEntity), mediaType);

            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/transactions")
                    .method("POST", body)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Create_transaction", jsonData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
