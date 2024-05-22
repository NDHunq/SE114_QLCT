package com.example.qlct;

import com.example.qlct.API_Entity.LoginResponse;
import com.example.qlct.API_Entity.SharedDaTa;

public class API_Config {
    public static String SERVER = "http://13.215.179.128";
    public static String API_VERSION = "api/v1";
    public static  String TEST_LOGIN_TOKEN = null;
    public void setTestLoginToken(String token)
    {
        TEST_LOGIN_TOKEN = token;
    }




}


