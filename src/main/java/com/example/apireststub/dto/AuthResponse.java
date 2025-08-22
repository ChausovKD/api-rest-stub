package com.example.apireststub.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuthResponse {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String login;
    private String password;
    private String date;

    public AuthResponse(String login, String password) {
        this.login = login;
        this.password = password;
        this.date = LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public AuthResponse() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
