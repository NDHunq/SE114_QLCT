package com.example.qlct.API_Entity;

import java.io.Serializable;

public class GetAllTransactionsEntity_quyen implements Serializable {
    public String id;
    public String user_id;
    public String amount;
    public String category_id;
    public String wallet_id;
    public String notes;
    public String picture;
    public String transaction_date;
    public String transaction_type;
    public String currency_unit;
    public String target_wallet_id;
    public Wallet wallet;
    public Category category;

    public GetAllTransactionsEntity_quyen(String id, String user_id, String amount, String category_id, String wallet_id, String notes, String picture, String transaction_date, String transaction_type, String currency_unit, String target_wallet_id, Wallet wallet, Category category) {
        this.id = id;
        this.user_id = user_id;
        this.amount = amount;
        this.category_id = category_id;
        this.wallet_id = wallet_id;
        this.notes = notes;
        this.picture = picture;
        this.transaction_date = transaction_date;
        this.transaction_type = transaction_type;
        this.currency_unit = currency_unit;
        this.target_wallet_id = target_wallet_id;
        this.wallet = wallet;
        this.category = category;
    }
}
