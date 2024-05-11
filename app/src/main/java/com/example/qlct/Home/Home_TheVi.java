package com.example.qlct.Home;

import com.example.qlct.API_Entity.GetAllWalletsEntity;

public class Home_TheVi {

    public String id;
    private int HinhAnh;
    private String TenVi;
    private String SoTien;
    GetAllWalletsEntity item;
    public int duocchon=0;
    public Home_TheVi(String id, int hinhAnh, String tenVi, String soTien) {
        this.id = id;
        HinhAnh = hinhAnh;
        TenVi = tenVi;
        SoTien = soTien;
    }

    public Home_TheVi(String id, int hinhAnh, String tenVi, String soTien, GetAllWalletsEntity item,int duocchon) {
        this.id = id;
        HinhAnh = hinhAnh;
        TenVi = tenVi;
        SoTien = soTien;
        this.item = item;
        this.duocchon=duocchon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetAllWalletsEntity getItem() {
        return item;
    }

    public void setItem(GetAllWalletsEntity item) {
        this.item = item;
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