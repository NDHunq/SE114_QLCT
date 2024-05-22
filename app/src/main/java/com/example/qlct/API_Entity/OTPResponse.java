package com.example.qlct.API_Entity;

public class OTPResponse {
   private Status status;
    private UserData data;

    // Getters and setters
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
    public class Status {
        private int code;
        private String message;

        // Getters and setters
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

    public static class UserData {
        private String username;
        private String phone_number;
        private String password;
        private String id;
        private String currency_unit;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhoneNumber() {
            return phone_number;
        }

        public void setPhoneNumber(String phone_number) {
            this.phone_number = phone_number;
        }

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

        public String getCurrencyUnit() {
            return currency_unit;
        }

        public void setCurrencyUnit(String currency_unit) {
            this.currency_unit = currency_unit;
        }
    }
}
