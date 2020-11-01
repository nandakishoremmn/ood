package com.splitwise.data;

public class User {
    int id;
    String name;
    String email;
    String mobile;

    public User(int id, String name, String email, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
