package com.example.qlct.API_Entity;

import java.io.Serializable;

public class CreateCategoryEntity implements Serializable {
    public String name;
    public String picture;
    public String type;

    public CreateCategoryEntity(String name, String picture, String type) {
        this.name = name;
        this.picture = picture;
        this.type = type;
    }
}
