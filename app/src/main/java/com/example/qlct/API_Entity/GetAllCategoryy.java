package com.example.qlct.API_Entity;

public class GetAllCategoryy {
    String id;
    String name;
    String picture;
    String type;
    String user_id;

    public GetAllCategoryy(String id, String name, String picture, String type, String user_id) {
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
}

