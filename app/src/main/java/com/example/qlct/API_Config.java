package com.example.qlct;

import com.example.qlct.API_Entity.LoginResponse;
import com.example.qlct.API_Entity.SharedDaTa;

public class API_Config {
    public static String SERVER = "https://expense-management-backend-jslp.onrender.com";
    public static String API_VERSION = "api/v1";
    public static  String TEST_LOGIN_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsInBob25lX251bWJlciI6Iis4NDkzMzUxNjQzNCIsInBhc3N3b3JkIjoiaGFwaHV0aGluaCIsImlhdCI6MTcxNTM0NjY0NCwiZXhwIjoxNzE2MDY2NjQ0fQ.J7YKRMrZ2g1Xx4xj89wzsYUZIJP2EQYYfIOR8XAoDE4";
    public void setLoginToken()
    {
        LoginResponse loginResponse = SharedDaTa.getInstance().getLoginResponse();
        TEST_LOGIN_TOKEN = loginResponse.getData().getUser().getPhone_number();
    }
}
