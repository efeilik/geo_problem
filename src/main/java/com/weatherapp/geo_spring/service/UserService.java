package com.weatherapp.geo_spring.service;

import com.weatherapp.geo_spring.dto.request.UserRequest;
import com.weatherapp.geo_spring.dto.response.GoogleApiResponse;
import com.weatherapp.geo_spring.enums.Role;
import com.weatherapp.geo_spring.model.User;
import com.weatherapp.geo_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IGoogleService googleService;

    @Override
    public void createUser(UserRequest userRequest) {
        GoogleApiResponse response = googleService.getGeocodingData(userRequest.getAddress());

        double latitude = response.getResults().get(0).getGeometry().getLocation().getLat();
        double longitude = response.getResults().get(0).getGeometry().getLocation().getLng();

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Role role = (userRequest.getRole() != null) ?
                Role.valueOf(userRequest.getRole().toUpperCase()) : Role.ROLE_USER;        user.setAddress(userRequest.getAddress());
        user.setRole(role);
        user.setLatitude(latitude);
        user.setLongitude(longitude);

        userRepository.save(user);
    }
}