package com.example.qlct.Home;

public class Home_The_member {
    private int HinhAnh;
    private String username;
    private String email;

    public Home_The_member(int hinhAnh, String username, String email) {
        HinhAnh = hinhAnh;
        this.username = username;
        this.email = email;
    }

    public int getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
