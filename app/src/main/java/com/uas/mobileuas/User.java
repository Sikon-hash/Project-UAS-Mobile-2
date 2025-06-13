package com.uas.mobileuas;

public class User {
    public String name, email;
    public Long phone;

    public User() { }

    public User(String name, Long phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
