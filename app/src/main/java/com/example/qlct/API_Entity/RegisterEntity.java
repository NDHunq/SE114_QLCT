package com.example.qlct.API_Entity;

public class RegisterEntity {
    private String username;
    private String phone_number;
    private String password;

    public RegisterEntity(String username, String phone_number, String password) {
        this.username = username;
        this.phone_number = phone_number;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
