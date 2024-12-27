package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.UserRequest;
import com.weatherapp.geo_spring.dto.response.GoogleApiResponse;
import com.weatherapp.geo_spring.enums.Role;
import com.weatherapp.geo_spring.model.User;
import com.weatherapp.geo_spring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IGoogleService googleService;

    @Mock
    private IDistanceCalculator distanceCalculator;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUser_ShouldCreateUserSuccessfully() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("test");
        userRequest.setEmail("test@test.com");
        userRequest.setPassword("test");
        userRequest.setRole("ROLE_ADMIN");
        userRequest.setAddress("test");

        GoogleApiResponse googleApiResponse = new GoogleApiResponse();
        googleApiResponse.setResults(Arrays.asList(
                new GoogleApiResponse.Result(new GoogleApiResponse.Geometry(new GoogleApiResponse.Location(40.0, 30.0)))
        ));

        when(googleService.getGeocodingData(userRequest.getAddress())).thenReturn(googleApiResponse);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");

        userService.createUser(userRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void findUserByEmail_ShouldReturnUserIfExists() {

        String email = "test@test.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setName("test");
        user.setPassword("test");
        user.setRole(Role.ROLE_USER);
        user.setAddress("test");
        user.setLatitude(1);
        user.setLongitude(1);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    public void findNearbyUsers_ShouldReturnUsersWithinRadius() {
        double problemLat = 40.0;
        double problemLng = 30.0;
        double radiusKm = 5.0;

        User user1 = new User();
        user1.setLatitude(40.0);
        user1.setLongitude(30.0);

        User user2 = new User();
        user2.setLatitude(41.0);
        user2.setLongitude(31.0);

        when(distanceCalculator.calculateDistance(problemLat, problemLng, user1.getLatitude(), user1.getLongitude()))
                .thenReturn(0.0);
        when(distanceCalculator.calculateDistance(problemLat, problemLng, user2.getLatitude(), user2.getLongitude()))
                .thenReturn(6.0);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        var nearbyUsers = userService.findNearbyUsers(problemLat, problemLng, radiusKm);

        assertEquals(1, nearbyUsers.size());
        assertTrue(nearbyUsers.contains(user1));
    }
}
