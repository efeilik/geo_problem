package com.weatherapp.geo_spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProblemRequest {

    @NotBlank(message = "Address cannot be null or empty")
    private String address;

    @NotBlank(message = "Description cannot be null or empty")
    private String description;
}
