package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.model.Problem;
import com.weatherapp.geo_spring.model.User;

import java.util.List;

public interface IEmailService {

    void sendEmailsForProblem(Problem problem, List<User> nearbyUsers);
}
