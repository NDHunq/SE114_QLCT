package com.example.qlct.Category;

public class Category_hdp {
    private String category_id;
    private String category_name;
    private String image;

    private String category_type; //1: Income, 2: Expense

//    public Category_hdp(String category_name, int image){
//        this.category_name = category_name;
//        this.image = image;
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Category_hdp) {
            Category_hdp category = (Category_hdp) obj;
            return this.category_id.equals(category.category_id);
        }
        return false;
    }
    public Category_hdp(String category_id, String category_name, String image, String category_type){
        this.category_id = category_id;
        this.category_name = category_name;
        this.image = image;
        this.category_type = category_type;
    }

    public Category_hdp(String category_name, String category_type){
        this.category_name = category_name;
        this.category_type = category_type;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getImageURL() {
        return image;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setImageURL(String image) {
        this.image = image;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }
}
