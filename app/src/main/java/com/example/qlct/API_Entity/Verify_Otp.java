package com.example.qlct.API_Entity;

public class Verify_Otp {
    private Status status;
    private Data data;

    public Verify_Otp(Status status, Data data) {
        this.status = status;
        this.data = data;
    }
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

    public class Status {
    private int code;
    private String message;

        public Status(int code, String message) {
            this.code = code;
            this.message = message;
        }
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

public class Data {
    private String username;
    private String phone_number;
    private String password;
    private String id;
    private String currency_unit;

    public Data(String username, String phone_number, String password, String id, String currency_unit) {
        this.username = username;
        this.phone_number = phone_number;
        this.password = password;
        this.id = id;
        this.currency_unit = currency_unit;
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

    public String getCurrency_unit() {
        return currency_unit;
    }

    public void setCurrency_unit(String currency_unit) {
        this.currency_unit = currency_unit;
    }
}
}
