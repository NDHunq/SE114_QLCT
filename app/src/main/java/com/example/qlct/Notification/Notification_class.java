package com.example.qlct.Notification;

public class Notification_class {
    String header;
    private int imageResId;


    String content;
    int seen;

    public Notification_class(String header, String content, int imageResId, int seen) {
        this.header = header;
        this.content = content;
        this.imageResId = imageResId;
        this.seen = seen;
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
