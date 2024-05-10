package com.example.qlct;

public class Category {
    private String category_name;
    private int image;

    private int category_type; //1: Income, 2: Expense, 3: Transfer

//    public Category(String category_name, int image){
//        this.category_name = category_name;
//        this.image = image;
//    }

    public Category(String category_name, int image, int category_type){
        this.category_name = category_name;
        this.image = image;
        this.category_type = category_type;
    }

    public Category(String category_name, int category_type){
        this.category_name = category_name;
        this.category_type = category_type;
    }

    public String getCategory_name() {
        return category_name;
    }

    public int getImage() {
        return image;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCategory_type() {
        return category_type;
    }

    public void setCategory_type(int category_type) {
        this.category_type = category_type;
    }
}
