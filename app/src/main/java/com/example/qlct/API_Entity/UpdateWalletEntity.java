package com.example.qlct.API_Entity;

public class UpdateWalletEntity {
    public String name;
    public Double amount;
    public String currency_unit;

    public UpdateWalletEntity(String name, Double amount, String currency_unit) {
        this.name = name;
        this.amount = amount;
        this.currency_unit = currency_unit;
    }
}
