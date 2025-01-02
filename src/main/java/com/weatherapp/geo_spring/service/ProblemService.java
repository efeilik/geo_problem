package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.ProblemRequest;
import com.weatherapp.geo_spring.dto.response.GoogleApiResponse;
import com.weatherapp.geo_spring.exceptions.ProblemNotFoundException;
import com.weatherapp.geo_spring.exceptions.ProblemTakenException;
import com.weatherapp.geo_spring.exceptions.UserNotFoundException;
import com.weatherapp.geo_spring.messaging.RabbitMQProducer;
import com.weatherapp.geo_spring.model.Problem;
import com.weatherapp.geo_spring.model.ProblemUser;
import com.weatherapp.geo_spring.model.User;
import com.weatherapp.geo_spring.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProblemService implements IProblemService {

    private final ProblemRepository problemRepository;
    private final IGoogleService googleService;
    private final IUserService userService;
    private final IProblemUserService problemUserService;
    private final RabbitMQProducer rabbitMQProducer;

    @Override
    public Problem save(ProblemRequest problemRequest) {
        GoogleApiResponse response = googleService.getGeocodingData(problemRequest.getAddress());

        double latitude = response.getResults().get(0).getGeometry().getLocation().getLat();
        double longitude = response.getResults().get(0).getGeometry().getLocation().getLng();

        Problem problem = new Problem();
        problem.setAddress(problemRequest.getAddress());
        problem.setDescription(problemRequest.getDescription());
        problem.setLatitude(latitude);
        problem.setLongitude(longitude);
        problem.setTaken(false);
        problem.setUniqueCode(UUID.randomUUID().toString());
        problemRepository.save(problem);
        rabbitMQProducer.sendMessage(problem);

        return problem;
    }

    @Override
    public void takeProblem(String email, String uniqueCode) {
        Problem problem = problemRepository.findByUniqueCode(uniqueCode);
        if(problem == null) {
            throw new ProblemNotFoundException(uniqueCode);
        }
        if(problem.isTaken()){
            throw new ProblemTakenException();
        }
            User user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(email));

            ProblemUser problemUser = new ProblemUser();
            problemUser.setProblem(problem);
            problemUser.setUser(user);
            problemUser.setTakenAt(LocalDateTime.now());

            problemUserService.createProblemUser(problemUser);

            problem.setTaken(true);
            problemRepository.save(problem);


    }

    @Override
    public List<Problem> readAll() {
        return problemRepository.findAll();
    }
}
