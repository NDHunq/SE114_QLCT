package com.example.qlct.API_Entity;

import java.time.LocalDateTime;

public class CreateRenewBudgetEntity {
    public String category_id;
    public double limit_amount;
    public String renew_date_unit;
    public String custom_renew_date;
    public boolean enable_notification;

    public CreateRenewBudgetEntity(String category_id, double limit_amount, String renew_date_unit, String custom_renew_date, boolean enable_notification) {
        this.category_id = category_id;
        this.limit_amount = limit_amount;
        this.renew_date_unit = renew_date_unit;
        this.custom_renew_date = custom_renew_date;
        this.enable_notification = enable_notification;
    }

    public CreateRenewBudgetEntity(String category_id, double limit_amount, String renew_date_unit, boolean enable_notification) {
        this.category_id = category_id;
        this.limit_amount = limit_amount;
        this.renew_date_unit = renew_date_unit;
        this.enable_notification = enable_notification;
    }
}
