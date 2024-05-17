package com.example.qlct;

import java.text.DecimalFormat;
import java.text.DecimalFormat;

public class doitiente {

    public double USDtoVND;
    public double UERtoVND;
    public double CNYtoVND;
    public double VNDtoUER;
    public double VNDtoCNY;
    public double VNDtoUSD;


    public doitiente(double VNDtoUSD, double VNDtoUER, double VNDtoCNY) {
        this.VNDtoUSD = VNDtoUSD;
        this.VNDtoUER = VNDtoUER;
        this.VNDtoCNY = VNDtoCNY;
        this.USDtoVND = 1 / VNDtoUSD;
        this.UERtoVND = 1 / VNDtoUER;
        this.CNYtoVND = 1 / VNDtoCNY;
    }
    public doitiente() {
        this.VNDtoUSD = 1/25455;
        this.VNDtoUER = 1/27462.13;
        this.VNDtoCNY = 1/3522.40;
        this.USDtoVND = 25455;
        this.UERtoVND = 27462.13;
        this.CNYtoVND = 3522.40;
    }



    public double getUSDtoVND() {
        return USDtoVND;
    }


    public String formatValue(double value) {
        String sign = "";
        if (value < 0) {
            sign = "-";
            value = Math.abs(value); // Lấy giá trị tuyệt đối của số
        }

        String[] units = new String[] {"", "K", "M", "B", "T"};
        int unitIndex = 0;

        while (value >= 1000 && unitIndex < units.length - 1) {
            value /= 1000;
            unitIndex++;
        }

        DecimalFormat df = new DecimalFormat("0.###");
        return sign + df.format(value) + units[unitIndex]; // Thêm lại dấu vào kết quả
    }
    public Double converttoVND( String currency_unit,Double amount)
    {
        if(currency_unit.equals("VND"))
        {
            return amount;
        }
        if(currency_unit.equals("USD"))
        {
            return amount*USDtoVND;
        }
        if(currency_unit.equals("EUR"))
        {
            return amount*UERtoVND;
        }
        if(currency_unit.equals("CNY"))
        {
            return amount*CNYtoVND;
        }
        return 0.0;
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
