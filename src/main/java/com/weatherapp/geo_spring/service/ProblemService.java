package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.ProblemRequest;
import com.weatherapp.geo_spring.dto.response.GoogleApiResponse;
import com.weatherapp.geo_spring.model.Problem;
import com.weatherapp.geo_spring.model.User;
import com.weatherapp.geo_spring.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService implements IProblemService {

    private final ProblemRepository problemRepository;
    private final IGoogleService googleService;
    private final IEmailService emailService;
    private final IUserService userService;

    @Override
    public void create(ProblemRequest problemRequest) {
        GoogleApiResponse response = googleService.getGeocodingData(problemRequest.getAddress());

        double latitude = response.getResults().get(0).getGeometry().getLocation().getLat();
        double longitude = response.getResults().get(0).getGeometry().getLocation().getLng();

        Problem problem = new Problem();
        problem.setAddress(problemRequest.getAddress());
        problem.setDescription(problemRequest.getDescription());
        problem.setLatitude(latitude);
        problem.setLongitude(longitude);
        problem.setTaken(false);

        problemRepository.save(problem);

        List<User> nearbyUsers = userService.findNearbyUsers(problem.getLatitude(), problem.getLongitude(), 5.0);
        emailService.sendEmailsForProblem(problem, nearbyUsers);
    }

    @Override
    public List<Problem> readAll() {
        return problemRepository.findAll();
    }
}
