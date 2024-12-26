package com.weatherapp.geo_spring.controller;

import com.weatherapp.geo_spring.dto.request.ProblemRequest;
import com.weatherapp.geo_spring.dto.request.UserRequest;
import com.weatherapp.geo_spring.service.IProblemService;
import com.weatherapp.geo_spring.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IProblemService problemService;
    private final IUserService userService;

    @PostMapping("problems")
    public ResponseEntity<String> addProblem(@RequestBody ProblemRequest problemRequest) {
        System.out.println(problemRequest);
        problemService.create(problemRequest);
        return ResponseEntity.ok("Problem is succesfully added");
    }

    @GetMapping("problems")
    public ResponseEntity<String> getAllProblems() {
        return ResponseEntity.ok(problemService.readAll().toString());
    }

    @PostMapping("users")
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
        try {
            userService.createUser(userRequest);
            return ResponseEntity.ok("User is successfully created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred: " + e.getMessage());
        }
    }
}
