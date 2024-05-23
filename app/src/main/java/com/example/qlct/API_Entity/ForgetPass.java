package com.example.qlct.API_Entity;

public class ForgetPass {
    public String new_password;
    public String otp_code;
    public String phone_number;

    public ForgetPass(String new_password, String otp_code, String phone_number) {
        this.new_password = new_password;
        this.otp_code = otp_code;
        this.phone_number = phone_number;
    }

}
