package com.example.qlct;

import java.text.DecimalFormat;
import java.text.DecimalFormat;

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


    public String formatValue(double value) {
        String[] units = new String[] {"", "K", "M", "B", "T"};
        int unitIndex = 0;

        while (value >= 1000 && unitIndex < units.length - 1) {
            value /= 1000;
            unitIndex++;
        }

        DecimalFormat df = new DecimalFormat("0.###");
        return df.format(value) + units[unitIndex];
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
