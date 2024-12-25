package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.ProblemRequest;
import com.weatherapp.geo_spring.model.Problem;

import java.util.List;

public interface IProblemService {

    void save(ProblemRequest problemRequest);

    List<Problem> findAll();
}
