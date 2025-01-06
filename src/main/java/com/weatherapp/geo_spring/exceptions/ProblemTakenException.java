package com.weatherapp.geo_spring.exceptions;

public class ProblemTakenException extends RuntimeException {
    public ProblemTakenException() {
        super("Problem has already been taken");
    }
}
