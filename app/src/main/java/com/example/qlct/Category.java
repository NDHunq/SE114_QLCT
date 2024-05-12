package com.example.qlct;

public class Category {
    private String category_name;
    private int image;

    public Category(String category_name, int image){
        this.category_name = category_name;
        this.image = image;
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
}
