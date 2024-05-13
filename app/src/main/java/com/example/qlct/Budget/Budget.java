package com.example.qlct.Budget;

import android.media.Image;

import java.io.Serializable;

public class Budget implements Serializable {
    String id;
    private String category;
    private double max_money;
    private double curent_money;
    private String fromDate;
    private String toDate;
    private String Image;
    String type;

    public Budget(String category, double max_money, double curent_money, String fromDate, String toDate, String image, String type, String id) {
        this.category = category;
        this.max_money = max_money;
        this.curent_money = curent_money;
        this.fromDate = fromDate;
        this.toDate = toDate;
        Image = image;
        this.type = type;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "category='" + category + '\'' +
                ", max_money=" + max_money +
                ", curent_money=" + curent_money +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", Image=" + Image +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getMax_money() {
        return max_money;
    }

    public void setMax_money(double max_money) {
        this.max_money = max_money;
    }

    public double getCurent_money() {
        return curent_money;
    }

    public void setCurent_money(double curent_money) {
        this.curent_money = curent_money;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
