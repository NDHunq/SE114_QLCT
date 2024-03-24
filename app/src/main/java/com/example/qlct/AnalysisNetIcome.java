package com.example.qlct;

public class AnalysisNetIcome {
    int year;
    double income;
    double expense;

    public AnalysisNetIcome(int year, double income, double expense) {
        this.year = year;
        this.income = income;
        this.expense = expense;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
