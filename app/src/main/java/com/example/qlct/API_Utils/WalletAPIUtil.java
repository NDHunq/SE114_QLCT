package com.example.qlct.API_Utils;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlct.API_Config;
import com.example.qlct.API_Entity.CreateWalletEntity;
import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.example.qlct.API_Entity.UpdateWalletEntity;
import com.example.qlct.R;
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

public class WalletAPIUtil extends AppCompatActivity {
    private String SERVER = API_Config.SERVER;
    private String API_VERSION = API_Config.API_VERSION;
    /*
    Phía dưới này là token để test, trong thực tế cần lấy token này bằng cách gọi api /login,
     sau đó lưu token vào local storage, và gửi token này trong mỗi request
     */
    private String LOGIN_TOKEN = API_Config.TEST_LOGIN_TOKEN;

    public  ArrayList<GetAllWalletsEntity> getAllWalletAPI (){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Log.d("url", SERVER + "/" + API_VERSION + "/wallet/list");
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/wallet/list")
                    .method("GET", null)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Get_wallet", jsonData);
            JSONObject json = new JSONObject(jsonData);

            //Maping json to entity
            Type listType = new TypeToken<ArrayList<GetAllWalletsEntity>>(){}.getType();
            ArrayList<GetAllWalletsEntity> parseAPIList = new Gson().fromJson(json.getJSONObject("data").getJSONArray("rows").toString(), listType);

            return parseAPIList;
        }
        catch(Exception e) {
            //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
            e.printStackTrace();
            return null;
        }
    }
    public int doesWalletExist(String walletname)
    {
        int doesExist = 0;
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Log.d("url", SERVER + "/" + API_VERSION + "/wallet/list");
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/wallet/list")
                    .method("GET", null)
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Get_wallet", jsonData);
            JSONObject json = new JSONObject(jsonData);

            //Maping json to entity
            Type listType = new TypeToken<ArrayList<GetAllWalletsEntity>>(){}.getType();
            ArrayList<GetAllWalletsEntity> parseAPIList = new Gson().fromJson(json.getJSONObject("data").getJSONArray("rows").toString(), listType);
            for (GetAllWalletsEntity wallet : parseAPIList) {
                if (wallet.name.equals(walletname)) {
                    doesExist = 1;
                    break;
                }
            }
        }
        catch(Exception e) {
            //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
            e.printStackTrace();


        }
        return doesExist;
    }

    // Hàm gọi API:
    //walletId: id của wallet cần update
    public void updateWalletAPI(String walletId, UpdateWalletEntity updateWalletEntity) {
        try {
            //Tạo body cho request
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create( new Gson().toJson(updateWalletEntity), mediaType);
            Request request = new Request.Builder()

                    //Chú ý phần này, đọc doc để lấy end point đúng
                    .url( SERVER + "/" + API_VERSION + "/wallet/update/" + walletId)

                    //Chú ý luôn, đọc doc để lấy method đúng
                    .method("PUT", body)

                    //Cái này thường là đều có
                    .addHeader("Authorization", LOGIN_TOKEN)
                    .build();

            //Cái này là đều giống nhau, không cần quan tâm
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            Log.d("Response data", jsonData);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createWalletAPI( CreateWalletEntity createWalletEntity) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create( new Gson().toJson(createWalletEntity), mediaType);
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/wallet/create")
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

    public void deleteWallet(String walletId){
            try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url( SERVER + "/" + API_VERSION + "/wallet/" + walletId)
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
