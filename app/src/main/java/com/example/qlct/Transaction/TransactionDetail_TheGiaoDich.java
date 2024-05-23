package com.example.qlct.Transaction;

import androidx.annotation.Nullable;

import com.example.qlct.API_Entity.CreateCategoryEntity;
import com.example.qlct.API_Entity.CreateWalletEntity;
import com.example.qlct.Category.Category_hdp;
import com.example.qlct.Wallet_hdp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class TransactionDetail_TheGiaoDich implements Serializable {
    private String HinhAnh_TheGiaoDich;
    private String HinhAnh;
    private CreateCategoryEntity DanhMuc;
    private String SoTien;
    private String NgayThang;
    private String LoaiGiaoDich;
    private String GhiChu;

    private String donViTien;
    private CreateWalletEntity ViTien;
    private CreateWalletEntity ViDuocNhanTien;

    public TransactionDetail_TheGiaoDich(String hinhAnh_TheGiaoDich, String hinhAnh, CreateCategoryEntity GiaoDich, String soTien, String ngayThang, String loaiGiaoDich, String ghiChu, String donViTien, CreateWalletEntity viTien, CreateWalletEntity viDuocNhanTien) {
        HinhAnh_TheGiaoDich = hinhAnh_TheGiaoDich;
        HinhAnh = hinhAnh;
        this.DanhMuc = GiaoDich;
        SoTien = soTien;
        NgayThang = ngayThang;
        LoaiGiaoDich = loaiGiaoDich;
        GhiChu = ghiChu;
        this.donViTien = donViTien;
        ViTien = viTien;
        ViDuocNhanTien = viDuocNhanTien;
    }

    public String getHinhAnh_TheGiaoDich() {
        return HinhAnh_TheGiaoDich;
    }

    public void setHinhAnh_TheGiaoDich(String hinhAnh_TheGiaoDich) {
        HinhAnh_TheGiaoDich = hinhAnh_TheGiaoDich;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public void setGiaoDich(CreateCategoryEntity GiaoDich) {
        this.DanhMuc = GiaoDich;
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

    public void setViTien(CreateWalletEntity viTien) {
        ViTien = viTien;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public CreateCategoryEntity getDanhMuc() {
        return DanhMuc;
    }

    public String getTenDanhMuc() {
        return DanhMuc.name;
    }

    public String getLoaiDanhMuc() {
        return DanhMuc.name;
    }

    public String getSoTien() {
        return SoTien;
    }

    public String getNgayThang() {
        return NgayThang;
    }

    public LocalDate getNgayThang_LocalDate() {
        return LocalDate.parse(formatDate(NgayThang));
    }

    public String getLoaiGiaoDich() {
        return LoaiGiaoDich;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public CreateWalletEntity getViTien() {
        return ViTien;
    }

    public CreateWalletEntity getViDuocNhanTien() {
        return ViDuocNhanTien;
    }

    public void setViDuocNhanTien(CreateWalletEntity viDuocNhanTien) {
        ViDuocNhanTien = viDuocNhanTien;
    }

    public String getDonViTien() {
        return donViTien;
    }

    public void setDonViTien(String donViTien) {
        this.donViTien = donViTien;
    }

    public String formatDate(String inputDate) {
        try {
            // Tạo một đối tượng SimpleDateFormat với định dạng ngày giờ đầu vào
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Phân tích chuỗi ngày giờ đầu vào thành một đối tượng Date
            Date date = inputFormat.parse(inputDate);

            // Tạo một đối tượng SimpleDateFormat với định dạng ngày đầu ra
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Định dạng lại đối tượng Date thành chuỗi ngày và trả về
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
