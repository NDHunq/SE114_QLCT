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
        private List<String> message;

        // getters and setters

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public List<String> getMessage() {
            return message;
        }
    }

    public static class Data {
        private String status;
        private User user;
        private String token;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getCurrency_unit() {
            return currency_unit;
        }

        public void setCurrency_unit(String currency_unit) {
            this.currency_unit = currency_unit;
        }

        public List<Object> getUser_wallets() {
            return user_wallets;
        }

        public void setUser_wallets(List<Object> user_wallets) {
            this.user_wallets = user_wallets;
        }

        public List<Object> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<Object> transactions) {
            this.transactions = transactions;
        }

        public List<Object> getCategories() {
            return categories;
        }

        public void setCategories(List<Object> categories) {
            this.categories = categories;
        }
// getters and setters
    }
}

