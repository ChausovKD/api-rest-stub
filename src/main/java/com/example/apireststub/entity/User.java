package com.example.apireststub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String login;
    private String password;
    private LocalDateTime dateTime;
    private String email;

    public User(String login, String password, LocalDateTime date) {
        this.login = login;
        this.password = password;
        this.dateTime = date;
    }
}
