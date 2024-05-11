package com.example.qlct.API_Entity;

public class CreateNoRenewBudgetEntity {
    public String category_id;
    public double limit_amount;
    public String no_renew_date_unit;
    public String no_renew_date;
    public boolean enable_notification;

    public CreateNoRenewBudgetEntity(String category_id, double limit_amount, String no_renew_date_unit, String no_renew_date, boolean enable_notification) {
        this.category_id = category_id;
        this.limit_amount = limit_amount;
        this.no_renew_date_unit = no_renew_date_unit;
        this.no_renew_date = no_renew_date;
        this.enable_notification = enable_notification;
    }
}
