package com.example.qlct.API_Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.qlct.API_Config;
import com.example.qlct.API_Entity.ChangePassWord;
import com.example.qlct.API_Entity.CreateRenewBudgetEntity;
import com.example.qlct.API_Entity.CreateUserRegiterEntity;
import com.example.qlct.API_Entity.CreateWalletEntity;
import com.example.qlct.API_Entity.LoginEntity;
import com.example.qlct.API_Entity.RegisterEntity;
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

public void RegisterUser(RegisterEntity registerEntity) {
    new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            String jsonData="";
            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Log.d("Register","1");
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create( new Gson().toJson(registerEntity), mediaType);
                Log.d("Register","2");

                Request request = new Request.Builder()
                        .url( SERVER + "/" + API_VERSION + "/user/register")
                        .method("POST", body)
                        .build();
                Log.d("Register","3");
                Response response = client.newCall(request).execute();
                Log.d("Register","4");
                jsonData = response.body().string();
                Log.d("Register","5");
                if(jsonData.isEmpty())
                {
                    Log.d("Register", "Request failed with status code: " + response.code());
                }
                else {
                    Log.d("Register", "Request successful");
                }
                Log.d("Register", jsonData);
            }catch (Exception e) {
                Log.d("Register","Error:"+e.toString());;
            }
            return null;
        }
    }.execute();
}
    public interface OnTaskCompleted{
        void onTaskCompleted(String result);
    }

    public void Login(LoginEntity loginEntity, OnTaskCompleted listener) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String jsonData = "";
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
                    Log.d("Login","Error:"+e.toString());
                }
                return jsonData;
            }

            @Override
            protected void onPostExecute(String result) {
                listener.onTaskCompleted(result);
            }
        }.execute();
    }
    public void verifyPhoneNumber(String phoneNumber, String verifyToken, OnTaskCompleted listener) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String jsonData = "";
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();

                    String url = SERVER + "/" + API_VERSION + "/user/verify-phone-number?phone_number=" + phoneNumber + "&verify_token=" + verifyToken;

                    Request request = new Request.Builder()
                            .url(url)
                            .method("GET", null)
                            .build();

                    Response response = client.newCall(request).execute();

                    jsonData = response.body().string();

                    Log.d("VerifyPhoneNumber", jsonData);
                }catch (Exception e) {
                    Log.d("VerifyPhoneNumber","Error:"+e.toString());
                }
                return jsonData;
            }

            @Override
            protected void onPostExecute(String result) {
                listener.onTaskCompleted(result);
            }
        }.execute();
    }

    public void changePassword(ChangePassWord changePassWord, OnTaskCompleted listener) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String jsonData = "";
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();

                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(new Gson().toJson(changePassWord), mediaType);

                    Request request = new Request.Builder()
                            .url(SERVER + "/" + API_VERSION + "/user/change-password")
                            .method("PUT", body)
                            .addHeader("Authorization", LOGIN_TOKEN)
                            .build();

                    Response response = client.newCall(request).execute();

                    jsonData = response.body().string();

                    Log.d("ChangePassword", jsonData);
                } catch (Exception e) {
                    Log.d("ChangePassword", "Error:" + e.toString());
                }
                return jsonData;
            }

            @Override
            protected void onPostExecute(String result) {
                listener.onTaskCompleted(result);
            }
        }.execute();
    }


}
