package com.example.qlct.API_Entity;

import java.util.Date;

public class GetAllBudget {
String id;
String category_id;
String limit_amount;
String expensed_amount;
String currency_unit;
String budget_type;
String no_renew_date_unit;
String no_renew_date;
String renew_date_unit;
String custom_renew_date;
Boolean is_active;
Boolean enable_notification;
String create_at;
String user_id;
GetAllCategoryy category;

        public GetAllBudget(String id, String category_id, String limit_amount, String expensed_amount, String currency_unit, String budget_type, String no_renew_date_unit, String no_renew_date, String renew_date_unit, String custom_renew_date, Boolean is_active, Boolean enable_notification, String create_at, String user_id, GetAllCategoryy category) {
        this.id = id;
        this.category_id = category_id;
        this.limit_amount = limit_amount;
        this.expensed_amount = expensed_amount;
        this.currency_unit = currency_unit;
        this.budget_type = budget_type;
        this.no_renew_date_unit = no_renew_date_unit;
        this.no_renew_date = no_renew_date;
        this.renew_date_unit = renew_date_unit;
        this.custom_renew_date = custom_renew_date;
        this.is_active = is_active;
        this.enable_notification = enable_notification;
        this.create_at = create_at;
        this.user_id = user_id;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getLimit_amount() {
        return limit_amount;
    }

    public void setLimit_amount(String limit_amount) {
        this.limit_amount = limit_amount;
    }

    public String getExpensed_amount() {
        return expensed_amount;
    }

    public void setExpensed_amount(String expensed_amount) {
        this.expensed_amount = expensed_amount;
    }

    public String getCurrency_unit() {
        return currency_unit;
    }

    public void setCurrency_unit(String currency_unit) {
        this.currency_unit = currency_unit;
    }

    public String getBudget_type() {
        return budget_type;
    }

    public void setBudget_type(String budget_type) {
        this.budget_type = budget_type;
    }

    public String getNo_renew_date_unit() {
        return no_renew_date_unit;
    }

    public void setNo_renew_date_unit(String no_renew_date_unit) {
        this.no_renew_date_unit = no_renew_date_unit;
    }

    public String getNo_renew_date() {
        return no_renew_date;
    }

    public void setNo_renew_date(String no_renew_date) {
        this.no_renew_date = no_renew_date;
    }

    public String getRenew_date_unit() {
        return renew_date_unit;
    }

    public void setRenew_date_unit(String renew_date_unit) {
        this.renew_date_unit = renew_date_unit;
    }

    public String getCustom_renew_date() {
        return custom_renew_date;
    }

    public void setCustom_renew_date(String custom_renew_date) {
        this.custom_renew_date = custom_renew_date;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Boolean getEnable_notification() {
        return enable_notification;
    }

    public void setEnable_notification(Boolean enable_notification) {
        this.enable_notification = enable_notification;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public GetAllCategoryy getCategory() {
        return category;
    }

    public void setCategory(GetAllCategoryy category) {
        this.category = category;
    }
}
