package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.UserRequest;
import com.weatherapp.geo_spring.model.User;

import java.util.List;

public interface IUserService {
    void createUser(UserRequest userRequest);
    List<User> findNearbyUsers(double problemLat, double problemLng, double radiusKm);
}
