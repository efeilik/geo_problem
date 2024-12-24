package com.weatherapp.geo_spring.DTO;

import lombok.Data;

@Data
public class ProblemRequest {
    private String address;
    private String description;
}
