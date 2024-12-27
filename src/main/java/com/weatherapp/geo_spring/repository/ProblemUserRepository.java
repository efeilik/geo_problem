package com.weatherapp.geo_spring.repository;

import com.weatherapp.geo_spring.model.ProblemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemUserRepository extends JpaRepository<ProblemUser, Long> {
}
