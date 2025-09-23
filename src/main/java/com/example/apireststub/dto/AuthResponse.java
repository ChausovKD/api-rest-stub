package com.example.apireststub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
