package com.weatherapp.geo_spring.repository;

import com.weatherapp.geo_spring.model.ProblemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemUserRepository extends JpaRepository<ProblemUser, Long> {
    List<ProblemUser> findByUserEmail(String email);
}
