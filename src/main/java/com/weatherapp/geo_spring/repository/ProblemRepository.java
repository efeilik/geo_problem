package com.weatherapp.geo_spring.repository;

import com.weatherapp.geo_spring.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
