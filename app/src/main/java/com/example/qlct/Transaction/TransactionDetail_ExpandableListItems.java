package com.example.qlct.Transaction;

import java.time.LocalDate;
import java.util.Locale;

public class TransactionDetail_ExpandableListItems {
    private LocalDate time;
    private String totalIncome;
    private String totalExpense;

    public TransactionDetail_ExpandableListItems() {
        time = LocalDate.now();
        totalIncome = "";
        totalExpense = "";
    }

    public TransactionDetail_ExpandableListItems(LocalDate time, String totalIncome, String totalExpense) {
        this.time = time;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
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

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(String totalExpense) {
        this.totalExpense = totalExpense;
    }
}
