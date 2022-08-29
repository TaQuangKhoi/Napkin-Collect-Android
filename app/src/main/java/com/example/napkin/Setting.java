package com.example.napkin;

public class Setting {
    String Email;
    String Token;

    public Setting(String email, String token) {
        Email = email;
        Token = token;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
