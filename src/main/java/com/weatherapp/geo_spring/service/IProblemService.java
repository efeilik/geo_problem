package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.ProblemRequest;
import com.weatherapp.geo_spring.model.Problem;

import java.util.List;

public interface IProblemService {

    Problem save(ProblemRequest problemRequest);

    List<Problem> readAll();

    void takeProblem(String email, String uniqueCode);
}
