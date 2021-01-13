package com.example.tomato;

public class UsersData {
    private String Username;
    private String Email;
    private String Password;

    public UsersData(String userId, String username, String email, String password) {
        Username = username;
        Email = email;
        Password = password;
    }

    public UsersData() {
    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}