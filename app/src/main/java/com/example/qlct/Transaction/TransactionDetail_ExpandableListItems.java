package com.example.qlct.Transaction;

import java.time.LocalDate;
import java.util.Locale;

public class TransactionDetail_ExpandableListItems {
    private LocalDate time;
    private double totalIncome;
    private double totalExpense;
    private String currency;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TransactionDetail_ExpandableListItems) {
            TransactionDetail_ExpandableListItems temp = (TransactionDetail_ExpandableListItems) obj;
            return temp.getTime().equals(this.getTime());
        }
        return false;
    }

    public TransactionDetail_ExpandableListItems() {
        time = LocalDate.now();
        totalIncome = 0;
        totalExpense = 0;
        currency = "VND";
    }

    public TransactionDetail_ExpandableListItems(LocalDate time, double totalIncome, double totalExpense, String currency) {
        this.time = time;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.currency = currency;
    }

    public int getDate() {
        return time.getDayOfMonth();
    }

    public String getDate_String(){
        if(time.getDayOfMonth() < 10)
            return "0" + time.getDayOfMonth();
        return time.getDayOfMonth() + "";
    }

    public int getMonth() {
        return time.getMonthValue();
    }

    public String getMonthName() {
        String temp = time.getMonth().name().toLowerCase(Locale.ROOT);
        return temp.substring(0, 1).toUpperCase(Locale.ROOT) + temp.substring(1);
    }

    public int getYear() {
        return time.getYear();
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
