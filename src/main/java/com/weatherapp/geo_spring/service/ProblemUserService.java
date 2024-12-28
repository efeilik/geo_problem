package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.model.ProblemUser;
import com.weatherapp.geo_spring.repository.ProblemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemUserService implements IProblemUserService {

    private final ProblemUserRepository problemUserRepository;

    @Override
    public void createProblemUser(ProblemUser problemUser) {
        problemUserRepository.save(problemUser);
    }

    @Override
    public List<ProblemUser> getProblemsByUserEmail(String email) {
        return problemUserRepository.findByUserEmail(email);
    }
}
