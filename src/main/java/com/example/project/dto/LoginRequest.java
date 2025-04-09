package com.example.project.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email is Requires")
    private String email;
    @NotBlank(message = "Password is Required")
    private String password;
}
