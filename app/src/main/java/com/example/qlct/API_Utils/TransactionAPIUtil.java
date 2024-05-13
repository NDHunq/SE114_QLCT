package com.example.qlct.API_Utils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlct.API_Config;

public class TransactionAPIUtil extends AppCompatActivity {
    private String SERVER = API_Config.SERVER;
    private String API_VERSION = API_Config.API_VERSION;
    /*
    Phía dưới này là token để test, trong thực tế cần lấy token này bằng cách gọi api /login,
     sau đó lưu token vào local storage, và gửi token này trong mỗi request
     */
    private String LOGIN_TOKEN = API_Config.TEST_LOGIN_TOKEN;


}
