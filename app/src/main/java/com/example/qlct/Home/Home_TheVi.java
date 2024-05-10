package com.example.qlct.Home;

import com.example.qlct.API_Entity.GetAllWalletsEntity;

public class Home_TheVi {

    public String id;
    private int HinhAnh;
    private String TenVi;
    private String SoTien;
    private GetAllWalletsEntity entity;

    public Home_TheVi(String id, int hinhAnh, String tenVi, String soTien,GetAllWalletsEntity entity) {
        this.id = id;
        HinhAnh = hinhAnh;
        TenVi = tenVi;
        SoTien = soTien;
        entity= entity;
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
    public GetAllWalletsEntity getEntity() {
        return entity;
    }

}
