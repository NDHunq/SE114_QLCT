package com.example.qlct.API_Entity;

import java.io.Serializable;

public class GetAllCategoryEntity implements Serializable  {
    public String id;
    public String name;
    public String picture;
    public String type;
    public String user_id;

    public GetAllCategoryEntity(String id, String name, String picture, String type, String user_id) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.type = type;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public String getType() {
        return type;
    }

    public String getUser_id() {
        return user_id;
    }
}



