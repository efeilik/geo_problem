package com.weatherapp.geo_spring.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be null or empty")
    private String password;
}
