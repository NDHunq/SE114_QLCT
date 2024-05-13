package com.example.qlct.API_Entity;

public class CreateCategoryEntity {
    String name;
    String picture;
    String type;
    String user_id;

    public CreateCategoryEntity(String name, String picture, String type, String user_id) {
        this.name = name;
        this.picture = picture;
        this.type = type;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
