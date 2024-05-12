package com.example.qlct.API_Entity;

import java.util.List;

public class LoginResponse {
    private Status status;
    private Data data;

    // getters and setters

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Status {
        private int code;
        private String message;

        // getters and setters

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class Data {
        private String status;
        private User user;
        private String token;

        // getters and setters
    }

    public static class User {
        private String id;
        private String username;
        private String phone_number;
        private String currency_unit;
        private List<Object> user_wallets;
        private List<Object> transactions;
        private List<Object> categories;

        // getters and setters
    }
}

