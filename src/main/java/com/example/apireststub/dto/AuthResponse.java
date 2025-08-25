package com.example.apireststub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuthResponse {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @NotBlank(message = "Login in required")
    @Size(min=6, max=25, message = "Login must be between 3 and 25 character")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{6,25}$",
            message = "Login must be 6-25 characters using letters, numbers, dots, underscores or hyphens")
    private String login;

    @NotBlank(message = "Password in required")
    @Size(min=6, max=30, message = "Password must be between 6 and 30 character")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,30}$",
            message = "Password must be 6-30 characters without spaces and contain at least one letter and one digit")
    private String password;

    private String date;

    public AuthResponse(String login, String password) {
        this.login = login;
        this.password = password;
        this.date = LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public AuthResponse() {
        this.date = LocalDateTime.now().format(DATE_TIME_FORMATTER);
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

    @Override
    public String toString() {
        return "AuthResponse{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
