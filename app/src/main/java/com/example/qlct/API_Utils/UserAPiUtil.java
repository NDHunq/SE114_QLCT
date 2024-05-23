package com.example.qlct.API_Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.qlct.API_Config;
import com.example.qlct.API_Entity.ChangePassWord;
import com.example.qlct.API_Entity.CreateRenewBudgetEntity;
import com.example.qlct.API_Entity.CreateUserRegiterEntity;
import com.example.qlct.API_Entity.CreateWalletEntity;
import com.example.qlct.API_Entity.ForgetPass;
import com.example.qlct.API_Entity.ForgetPassRP;
import com.example.qlct.API_Entity.GetAllCategoryEntity;
import com.example.qlct.API_Entity.GetAllCategoryy;
import com.example.qlct.API_Entity.LoginEntity;
import com.example.qlct.API_Entity.RegisterEntity;
import com.example.qlct.API_Entity.UserProfile;
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

//    public void getUserProfile(OnTaskCompleted listener) {
//        new AsyncTask<Void, Void, String>() {
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                Log.d("GetUserProfile", LOGIN_TOKEN);
//                String jsonData = "";
//                try {
//                    OkHttpClient client = new OkHttpClient().newBuilder()
//                            .build();
//
//                    Request request = new Request.Builder()
//                            .url(SERVER + "/" + API_VERSION + "/user/profile")
//                            .addHeader("Authorization", LOGIN_TOKEN)
//                            .method("GET", null)
//                            .build();
//
//                    Response response = client.newCall(request).execute();
//
//                    jsonData = response.body().string();
//
//                    Log.d("GetUserProfile", jsonData);
//                } catch (Exception e) {
//                    Log.d("GetUserProfile", "Error:" + e.toString());
//                }
//                return jsonData;
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                listener.onTaskCompleted(result);
//            }
//        }.execute();
//    }
public UserProfile getUserProfile (){
    try {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Log.d("url", SERVER + "/" + API_VERSION + "/user/profile");
        Request request = new Request.Builder()
                .url( SERVER + "/" + API_VERSION + "/user/profile")
                .method("GET", null)
                .addHeader("Authorization", LOGIN_TOKEN)
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        Log.d("userprofile", jsonData);
        JSONObject json = new JSONObject(jsonData);

        Type user = new TypeToken<UserProfile>(){}.getType();
       UserProfile userProfile = new Gson().fromJson(json.toString(), user);

        return userProfile;
    }
    catch(Exception e) {
        //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
       Log.d("Error", "Error: "+ e.toString());
        return null;
    }
}
    public void SendOtp(String phoneNumber) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder().build();

                    // Create a JSON object with the phone number
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("phone_number", phoneNumber);

                    // Convert the JSON object to a string
                    String json = jsonObject.toString();

                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(json, mediaType);

                    Request request = new Request.Builder()
                            .url(SERVER + "/" + API_VERSION + "/user/forget-password")
                            .addHeader("Content-Type", "application/json")
                            .method("POST", body)
                            .build();

                    Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    Log.d("ForgetPassword", jsonData);
                } catch (Exception e) {
                    Log.d("ForgetPassword", "Error:" + e.toString());
                }
                return null;
            }
        }.execute();
    }
    public void resetPassword(ForgetPass forgetPass, OnTaskCompleted listener) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String jsonData = "";
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();

                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create( new Gson().toJson(forgetPass), mediaType);

                    Request request = new Request.Builder()
                            .url( SERVER + "/" + API_VERSION + "/user/reset-password")
                            .method("POST", body)
                            .build();

                    Response response = client.newCall(request).execute();

                    jsonData = response.body().string();

                    Log.d("forgetpass", jsonData);
                }catch (Exception e) {
                    Log.d("forgetpass","Error:"+e.toString());
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
