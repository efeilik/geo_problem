package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.ProblemRequest;
import com.weatherapp.geo_spring.dto.response.GoogleApiResponse;
import com.weatherapp.geo_spring.enums.Role;
import com.weatherapp.geo_spring.messaging.RabbitMQProducer;
import com.weatherapp.geo_spring.model.Problem;
import com.weatherapp.geo_spring.model.ProblemUser;
import com.weatherapp.geo_spring.model.User;
import com.weatherapp.geo_spring.repository.ProblemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProblemServiceTest {

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private IGoogleService googleService;

    @Mock
    private IUserService userService;

    @Mock
    private IProblemUserService problemUserService;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @InjectMocks
    private ProblemService problemService;

    private ProblemRequest problemRequest;

    @BeforeEach
    void setUp() {
        problemRequest = new ProblemRequest();
        problemRequest.setAddress("Test");
        problemRequest.setDescription("Test");
    }

    @Test
    void save_ShouldSaveProblemAndSendMessage() {

        GoogleApiResponse googleApiResponse = new GoogleApiResponse();
        googleApiResponse.setResults(Arrays.asList(
                new GoogleApiResponse.Result(new GoogleApiResponse.Geometry(new GoogleApiResponse.Location(40.0, 30.0)))
        ));

        when(googleService.getGeocodingData(problemRequest.getAddress())).thenReturn(googleApiResponse);

        problemService.save(problemRequest);

        verify(problemRepository, times(1)).save(any(Problem.class));
        verify(rabbitMQProducer, times(1)).sendMessage(any(Problem.class));
    }

    @Test
    void takeProblem_ShouldCreateProblemUserAndMarkProblemAsTaken() {

        String email = "test@example.com";
        String uniqueCode = "test";

        Problem problem = new Problem();
        problem.setId(1L);
        problem.setTaken(false);
        problem.setLongitude(1);
        problem.setLatitude(1);
        problem.setAddress("test");
        problem.setDescription("test");
        problem.setUniqueCode(uniqueCode);

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setName("test");
        user.setPassword("test");
        user.setRole(Role.ROLE_USER);
        user.setAddress("test");
        user.setLatitude(1);
        user.setLongitude(1);

        when(problemRepository.findByUniqueCode(uniqueCode)).thenReturn(problem);
        when(userService.findUserByEmail(email)).thenReturn(Optional.of(user));

        problemService.takeProblem(email, uniqueCode);

        verify(problemUserService, times(1)).createProblemUser(any(ProblemUser.class));
        verify(problemRepository, times(1)).save(problem);
        assertTrue(problem.isTaken());
    }

    @Test
    void takeProblem_ShouldThrowException_WhenProblemAlreadyTaken() {
        String uniqueCode = UUID.randomUUID().toString();
        String email = "test@example.com";

        Problem problem = new Problem();
        problem.setUniqueCode(uniqueCode);
        problem.setTaken(true);

        when(problemRepository.findByUniqueCode(uniqueCode)).thenReturn(problem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            problemService.takeProblem(email, uniqueCode);
        });

        assertEquals("Problem already taken or not found", exception.getMessage());
        verify(problemUserService, never()).createProblemUser(any(ProblemUser.class));
    }

    @Test
    void takeProblem_ShouldThrowException_WhenProblemNotFound() {
        String uniqueCode = "test";
        String email = "test@test.com";

        when(problemRepository.findByUniqueCode(uniqueCode)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            problemService.takeProblem(email, uniqueCode);
        });

        assertEquals("Problem already taken or not found", exception.getMessage());
        verify(problemUserService, never()).createProblemUser(any(ProblemUser.class));
    }

    @Test
    void takeProblem_ShouldThrowException_WhenUserNotFound() {
        String email = "test@test.com";
        String uniqueCode = UUID.randomUUID().toString();

        Problem problem = new Problem();
        problem.setUniqueCode(uniqueCode);
        problem.setTaken(false);

        when(problemRepository.findByUniqueCode(uniqueCode)).thenReturn(problem);
        when(userService.findUserByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            problemService.takeProblem(email, uniqueCode);
        });

        assertEquals("User not found with email: " + email, exception.getMessage());
        verify(problemUserService, never()).createProblemUser(any(ProblemUser.class));
    }

    @Test
    void readAll_ShouldReturnAllProblems() {
        problemService.readAll();
        verify(problemRepository, times(1)).findAll();
    }
}
