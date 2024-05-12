package com.example.qlct;

public class Wallet {
    private String walletName;
    private String amountMoney;
    private int image;


    public Wallet(String walletName, String amountMoney, int image) {
        this.walletName = walletName;
        this.amountMoney = amountMoney;
        this.image = image;
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

}
