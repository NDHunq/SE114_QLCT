package com.example.qlct.Notification;

public class Notification_class {
    String header;
    String id;
    private int imageResId;


    String content;
    int seen;
    String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Notification_class(String id, String header, String content, int imageResId, int seen, String date) {
        this.id = id;
        this.header = header;
        this.content = content;
        this.imageResId = imageResId;
        this.seen = seen;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }
}