package com.example.qlct;

import android.graphics.drawable.Drawable;

public class Category2 {
    private String category_name;
    private String image;

    public Category2(String category_name, String image){
        this.category_name = category_name;
        this.image = image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
