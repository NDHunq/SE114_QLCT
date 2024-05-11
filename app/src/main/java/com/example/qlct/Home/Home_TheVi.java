package com.example.qlct.Home;

public class Home_TheVi {

    public String id;
    private int HinhAnh;
    private String TenVi;
    private String SoTien;

    public Home_TheVi(String id, int hinhAnh, String tenVi, String soTien) {
        this.id = id;
        HinhAnh = hinhAnh;
        TenVi = tenVi;
        SoTien = soTien;
    }

    public void setHinhAnh(int hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public void setTenVi(String tenVi) {
        TenVi = tenVi;
    }

    public void setSoTien(String soTien) {
        SoTien = soTien;
    }

    public int getHinhAnh() {
        return HinhAnh;
    }

    public String getTenVi() {
        return TenVi;
    }

    public String getSoTien() {
        return SoTien;
    }
}
