package com.example.qlct;

public class doitiente {
    double USDtoVND;
    double UERtoVND;
    double CNYtoVND;
    double VNDtoUER;
    double VNDtoCNY;
    double VNDtoUSD;

    public doitiente(double VNDtoUSD, double VNDtoUER, double VNDtoCNY) {
        this.VNDtoUSD = VNDtoUSD;
        this.VNDtoUER = VNDtoUER;
        this.VNDtoCNY = VNDtoCNY;
        this.USDtoVND = 1 / VNDtoUSD;
        this.UERtoVND = 1 / VNDtoUER;
        this.CNYtoVND = 1 / VNDtoCNY;
    }

    public double getUSDtoVND() {
        return USDtoVND;
    }

    public void setUSDtoVND(double USDtoVND) {
        this.USDtoVND = USDtoVND;
    }

    public double getUERtoVND() {
        return UERtoVND;
    }

    public void setUERtoVND(double UERtoVND) {
        this.UERtoVND = UERtoVND;
    }

    public double getCNYtoVND() {
        return CNYtoVND;
    }

    public void setCNYtoVND(double CNYtoVND) {
        this.CNYtoVND = CNYtoVND;
    }

    public double getVNDtoUER() {
        return VNDtoUER;
    }

    public void setVNDtoUER(double VNDtoUER) {
        this.VNDtoUER = VNDtoUER;
    }

    public double getVNDtoCNY() {
        return VNDtoCNY;
    }

    public void setVNDtoCNY(double VNDtoCNY) {
        this.VNDtoCNY = VNDtoCNY;
    }

    public double getVNDtoUSD() {
        return VNDtoUSD;
    }

    public void setVNDtoUSD(double VNDtoUSD) {
        this.VNDtoUSD = VNDtoUSD;
    }
}
