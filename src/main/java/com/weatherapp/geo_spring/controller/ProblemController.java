package com.weatherapp.geo_spring.controller;

import com.weatherapp.geo_spring.service.IProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final IProblemService problemService;

    @PostMapping("/take/{uniqueCode}")
    public ResponseEntity<String> takeProblem(@PathVariable String uniqueCode, @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            String email = userDetails.getUsername();
            problemService.takeProblem(email, uniqueCode);
            return ResponseEntity.ok("Problem successfully taken.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
