package com.weatherapp.geo_spring.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String address;
    private String role;
}
