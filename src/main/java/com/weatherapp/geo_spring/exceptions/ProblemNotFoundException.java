package com.weatherapp.geo_spring.exceptions;

public class ProblemNotFoundException extends RuntimeException {
    public ProblemNotFoundException(String uniqueCode) {
        super("Could not find problem with unique code: " + uniqueCode);
    }
}
