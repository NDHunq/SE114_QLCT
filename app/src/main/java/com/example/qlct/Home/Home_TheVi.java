package com.example.qlct.Home;

public class Home_TheVi {
    private int HinhAnh;
    private String TenVi;
    private String SoTien;

    public Home_TheVi(int hinhAnh, String tenVi, String soTien) {
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
