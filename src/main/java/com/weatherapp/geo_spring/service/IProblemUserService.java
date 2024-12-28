package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.model.ProblemUser;

import java.util.List;

public interface IProblemUserService {
    void createProblemUser(ProblemUser problemUser);

    List<ProblemUser> getProblemsByUserEmail(String email);
}
