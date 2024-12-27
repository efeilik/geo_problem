package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.enums.Role;
import com.weatherapp.geo_spring.model.Problem;
import com.weatherapp.geo_spring.model.ProblemUser;
import com.weatherapp.geo_spring.model.User;
import com.weatherapp.geo_spring.repository.ProblemUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class ProblemUserServiceTest {

    @Mock
    private ProblemUserRepository problemUserRepository;

    @InjectMocks
    private ProblemUserService problemUserService;

    @Test
    public void createProblemUser_ShouldSaveProblemUser() {

        ProblemUser problemUser = new ProblemUser();
        problemUser.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setName("test");
        user.setPassword("test");
        user.setRole(Role.ROLE_USER);
        user.setAddress("test");
        user.setLatitude(1);
        user.setLongitude(1);
        problemUser.setUser(user);

        Problem problem = new Problem();
        problem.setId(1L);
        problem.setTaken(false);
        problem.setLongitude(1);
        problem.setLatitude(1);
        problem.setAddress("test");
        problem.setDescription("test");
        problem.setUniqueCode("test");

        problemUser.setProblem(problem);

        problemUserService.createProblemUser(problemUser);

        verify(problemUserRepository, times(1)).save(problemUser);
    }
}
