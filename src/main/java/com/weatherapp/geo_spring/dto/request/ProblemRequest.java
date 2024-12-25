package com.weatherapp.geo_spring.dto.request;

import lombok.Data;

@Data
public class ProblemRequest {
    private String address;
    private String description;
}
