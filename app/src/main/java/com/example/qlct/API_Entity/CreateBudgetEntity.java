package com.example.qlct.API_Entity;

public class CreateBudgetEntity {
    public String category_id;
    public double limit_amount;
    public String cron;

    public CreateBudgetEntity(String category_id, double amount, String cron) {
        this.category_id = category_id;
        this.limit_amount = amount;
        this.cron = cron;
    }
}
