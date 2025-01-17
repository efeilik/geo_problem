package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.UserRequest;
import com.weatherapp.geo_spring.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(UserRequest userRequest);
    List<User> findNearbyUsers(double problemLat, double problemLng, double radiusKm);
    Optional<User> findUserByEmail(String email);
    User saveUser(User user);
    List<User> readAll();
}
