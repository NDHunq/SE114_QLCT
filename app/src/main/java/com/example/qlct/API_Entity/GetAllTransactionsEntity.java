package com.example.qlct.API_Entity;

public class GetAllTransactionsEntity {
    public String id;
    public String user_id;
    public Integer amount;
    public String category_id;
    public String wallet_id;
    public String notes;
    public String picture;
    public String transaction_date;
    public String transaction_type;
    public String currency_unit;
    public String target_wallet_id;
    public CreateWalletEntity wallet;
    public CreateCategoryEntity category;
}
