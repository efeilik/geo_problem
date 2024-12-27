package com.weatherapp.geo_spring.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotBlank(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be null or empty")
    private String password;

    @NotBlank(message = "Address cannot be null or empty")
    private String address;

    private String role;
}
