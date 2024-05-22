package com.example.qlct;

public class Wallet_hdp {
    private String id;
    private String walletName;
    private String amountMoney;
    private int image;

    private String currency;


    public Wallet_hdp(String walletName, String amountMoney, int image) {
        this.walletName = walletName;
        this.amountMoney = amountMoney;
        this.image = image;
    }

    public Wallet_hdp(String id, String walletName, String amountMoney, int image, String currency) {
        this.id = id;
        this.walletName = walletName;
        this.amountMoney = amountMoney;
        this.image = image;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName){
        this.walletName = walletName;
    }

    public String getAmountMoney(){
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney){
        this.amountMoney = amountMoney;
    }

    public int getImage(){
        return image;
    }

    public void setImage(int image){
        this.image = image;
    }

    public String getCurrency(){
        return currency;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

}
