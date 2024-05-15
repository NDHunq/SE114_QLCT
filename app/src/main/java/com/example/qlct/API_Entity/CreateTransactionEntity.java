package com.example.qlct.API_Entity;

public class CreateTransactionEntity {
    public Integer amount;
    public String transaction_date;
    public String category_id;
    public String wallet_id;
    public String notes;
    public String picture;
    public String transaction_type;
    public String currency_unit;
    public String target_wallet_id;

    public CreateTransactionEntity(Integer amount, String transaction_date, String category_id, String wallet_id, String notes, String picture, String transaction_type, String currency_unit, String target_wallet_id) {
        this.amount = amount;
        this.transaction_date = transaction_date;
        this.category_id = category_id;
        this.wallet_id = wallet_id;
        this.notes = notes;
        this.picture = picture;
        this.transaction_type = transaction_type;
        this.currency_unit = currency_unit;
        this.target_wallet_id = target_wallet_id;
    }
}
