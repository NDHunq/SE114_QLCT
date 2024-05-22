package com.example.qlct;

import com.example.qlct.API_Entity.LoginResponse;
import com.example.qlct.API_Entity.SharedDaTa;

public class API_Config {
    public static String SERVER = "https://expense-management-backend-2tac.onrender.com";
    public static String API_VERSION = "api/v1";
    public static  String TEST_LOGIN_TOKEN = null;
    public void setTestLoginToken(String token)
    {
        TEST_LOGIN_TOKEN = token;
    }

}
