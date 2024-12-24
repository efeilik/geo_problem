package com.weatherapp.geo_spring.controller;

import com.weatherapp.geo_spring.DTO.ProblemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/problems")
    public ResponseEntity<String> addProblem(@RequestBody ProblemRequest problemRequest) {

    }
}
