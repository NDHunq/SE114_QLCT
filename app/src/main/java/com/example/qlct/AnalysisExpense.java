package com.example.qlct;

public class AnalysisExpense {
    int color;
    int avt;
    String name;
    double percent;
    double money;

    public AnalysisExpense(int color, int avt, String name, double percent, double money) {
        this.color = color;
        this.avt = avt;
        this.name = name;
        this.percent = percent;
        this.money = money;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAvt() {
        return avt;
    }

    public void setAvt(int avt) {
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
