package com.librarytask;

public class Patron {
    private int patronId;
    private String name;
    private String email;
    private String password;
    private String role;

    public Patron(int patronId, String name, String email, String password, String role) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Patron(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Patron(int patronId) {
        this.patronId = patronId;
    }

    public Patron(String email) {
        this.email = email;
    }

    public Patron(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // Other methods like updateProfile, viewTransactions, etc.
}
