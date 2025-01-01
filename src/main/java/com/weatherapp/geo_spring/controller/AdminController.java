package com.weatherapp.geo_spring.controller;

import com.weatherapp.geo_spring.dto.request.ProblemRequest;
import com.weatherapp.geo_spring.dto.request.UserRequest;
import com.weatherapp.geo_spring.model.Problem;
import com.weatherapp.geo_spring.model.User;
import com.weatherapp.geo_spring.service.IEmailService;
import com.weatherapp.geo_spring.service.IProblemService;
import com.weatherapp.geo_spring.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IProblemService problemService;
    private final IUserService userService;

    @PostMapping("problems")
    public ResponseEntity<Problem> addProblem(@RequestBody @Valid ProblemRequest problemRequest) {
        return ResponseEntity.ok(problemService.save(problemRequest));
    }

    @GetMapping("problems")
    public ResponseEntity<List<Problem>> getAllProblems() {
        return ResponseEntity.ok(problemService.readAll());
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.readAll());
    }

    @PostMapping("users")
    public ResponseEntity<User> addUser(@RequestBody @Valid UserRequest userRequest) {
            return ResponseEntity.ok(userService.createUser(userRequest));
    }
}
