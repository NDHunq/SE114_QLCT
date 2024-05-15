package com.example.qlct.Transaction;

import com.example.qlct.Category.Category_hdp;

public class TransactionDetail_TheGiaoDich {
    private int HinhAnh;
    private Category_hdp GiaoDich;
    private String SoTien;
    private String NgayThang;
    private String GhiChu;
    private String ViTien;

    public TransactionDetail_TheGiaoDich(int hinhAnh, Category_hdp GiaoDich, String soTien, String ngayThang, String ghiChu, String viTien) {
        HinhAnh = hinhAnh;
        this.GiaoDich = GiaoDich;
        SoTien = soTien;
        NgayThang = ngayThang;
        GhiChu = ghiChu;
        ViTien = viTien;
    }

    public void setHinhAnh(int hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public void setGiaoDich(Category_hdp GiaoDich) {
        this.GiaoDich = GiaoDich;
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

    public Category_hdp getGiaoDich() {
        return GiaoDich;
    }

    public String getTenGiaoDich() {
        return GiaoDich.getCategory_name();
    }

    public String getLoaiGiaoDich() {
        return GiaoDich.getCategory_type();
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
