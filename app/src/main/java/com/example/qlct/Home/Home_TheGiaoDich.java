package com.example.qlct.Home;

public class Home_TheGiaoDich {
    private int HinhAnh;
    private String TenGiaoDich;
    private String SoTien;
    private String NgayThang;
    private String GhiChu;
    private String ViTien;

    public Home_TheGiaoDich(int hinhAnh, String tenGiaoDich, String soTien, String ngayThang, String ghiChu, String viTien) {
        HinhAnh = hinhAnh;
        TenGiaoDich = tenGiaoDich;
        SoTien = soTien;
        NgayThang = ngayThang;
        GhiChu = ghiChu;
        ViTien = viTien;
    }

    public void setHinhAnh(int hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public void setTenGiaoDich(String tenGiaoDich) {
        TenGiaoDich = tenGiaoDich;
    }

    public void setSoTien(String soTien) {
        SoTien = soTien;
    }

    public void setNgayThang(String ngayThang) {
        NgayThang = ngayThang;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public void setViTien(String viTien) {
        ViTien = viTien;
    }

    public int getHinhAnh() {
        return HinhAnh;
    }

    public String getTenGiaoDich() {
        return TenGiaoDich;
    }

    public String getSoTien() {
        return SoTien;
    }

    public String getNgayThang() {
        return NgayThang;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public String getViTien() {
        return ViTien;
    }
}
