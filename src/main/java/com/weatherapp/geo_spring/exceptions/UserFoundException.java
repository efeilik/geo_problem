package com.weatherapp.geo_spring.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(String email) {
        super("User " + email + " was found");
    }
}
