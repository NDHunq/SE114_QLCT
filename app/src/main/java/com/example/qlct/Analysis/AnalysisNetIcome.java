package com.example.qlct.Analysis;

public class AnalysisNetIcome {
    int date;
    double income;
    double expense;

    public AnalysisNetIcome(int year, double income, double expense) {
        this.date = year;
        this.income = income;
        this.expense = expense;
    }

    public int getYear() {
        return date;
    }

    public void setYear(int year) {
        this.date = year;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}
