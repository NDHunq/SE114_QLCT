package com.example.qlct.API_Entity;

import java.util.List;

public class UserProfile {
    private Status status;
    private Data data;

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
// getters and setters

    public static class Status {
        private int code;
        private String message;

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

        // getters and setters
    }

    public static class Data {
        private String id;
        private String username;
        private String phone_number;
        private String password;
        private String currency_unit;
        private List<UserWallet> user_wallets;
        private List<Object> transactions;
        private List<Object> categories;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

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

        public List<UserWallet> getUser_wallets() {
            return user_wallets;
        }

        public void setUser_wallets(List<UserWallet> user_wallets) {
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

        public static class UserWallet {
            private String user_id;
            private String wallet_id;
            private String join_date;
            private boolean isAdmin;
            private Wallet wallet;

            // getters and setters

            public static class Wallet {
                private String id;
                private String name;
                private String amount;
                private String currency_unit;
                private String create_at;
                private String update_at;

                // getters and setters
            }
        }
    }
}
