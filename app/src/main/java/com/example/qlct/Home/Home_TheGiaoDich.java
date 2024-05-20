package com.example.qlct.Home;

public class Home_TheGiaoDich {
    private String HinhAnh;
    private String TenGiaoDich;
    private String SoTien;
    private String NgayThang;
    private String GhiChu;
    private String ViTien;
    public   String Typee;
    public String currency_unit;

    public Home_TheGiaoDich(String hinhAnh, String tenGiaoDich, String soTien,String dv, String ngayThang, String ghiChu, String viTien,String Type) {
        HinhAnh = hinhAnh;
        TenGiaoDich = tenGiaoDich;
        SoTien = soTien;
        NgayThang = ngayThang;
        currency_unit= dv;
        GhiChu = ghiChu;
        ViTien = viTien;
        this.Typee = Type;
    }

    public String getCurrencyUnit()
    {
        return currency_unit;
    }
    public void setHinhAnh(String hinhAnh) {
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

    public String getHinhAnh() {
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
