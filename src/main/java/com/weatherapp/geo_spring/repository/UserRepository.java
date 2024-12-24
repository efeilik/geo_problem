package com.weatherapp.geo_spring.repository;

import com.weatherapp.geo_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
