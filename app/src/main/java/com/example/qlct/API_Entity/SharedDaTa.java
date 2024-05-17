package com.example.qlct.API_Entity;

public class SharedDaTa {
    private static SharedDaTa instance = null;
    private LoginResponse loginResponse;

    private SharedDaTa() {
        // Private constructor to prevent instantiation
    }

    public static synchronized SharedDaTa getInstance() {
        if (instance == null) {
            instance = new SharedDaTa();
        }
        return instance;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
