package com.example.qlct.Home;

public class Home_The_Share_Wallet
{
    String email;
    String Message;

    public Home_The_Share_Wallet(String email, String message) {
        this.email = email;
        Message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
