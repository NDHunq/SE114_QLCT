package com.example.qlct.Analysis;

public class AnalysisExpense {
    int color;
    String avt;
    String name;
    double percent;
    String money;
    String currency;

    public AnalysisExpense(int color, String avt, String name, double percent, String money, String currency) {
        this.color = color;
        this.avt = avt;
        this.name = name;
        this.percent = percent;
        this.money = money;
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
