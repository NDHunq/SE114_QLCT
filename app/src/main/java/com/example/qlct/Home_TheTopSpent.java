package com.example.qlct;

public class Home_TheTopSpent {
    private int HinhAnh;
    private String tenCategory;
    private String soTien;
    private String percent;

    public Home_TheTopSpent(int hinhAnh, String tenCategory, String soTien) {
        HinhAnh = hinhAnh;
        this.tenCategory = tenCategory;
        this.soTien = soTien;

    }

    public int getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getTenCategory() {
        return tenCategory;
    }

    public void setTenCategory(String tenCategory) {
        this.tenCategory = tenCategory;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
