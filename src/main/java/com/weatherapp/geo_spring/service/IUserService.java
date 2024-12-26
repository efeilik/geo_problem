package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.UserRequest;

public interface IUserService {
    void createUser(UserRequest userRequest);
}
