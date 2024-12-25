package com.weatherapp.geo_spring.controller;

import com.weatherapp.geo_spring.dto.request.ProblemRequest;
import com.weatherapp.geo_spring.service.IProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IProblemService problemService;

    @PostMapping("problems")
    public ResponseEntity<String> addProblem(@RequestBody ProblemRequest problemRequest) {
        System.out.println(problemRequest);
        problemService.save(problemRequest);
        return ResponseEntity.ok("Problem is succesfully added");
    }

    @GetMapping("problems")
    public ResponseEntity<String> getAllProblems() {
        return ResponseEntity.ok(problemService.findAll().toString());
    }
}
