package com.weatherapp.geo_spring.config;

import com.weatherapp.geo_spring.enums.Role;
import com.weatherapp.geo_spring.model.User;
import com.weatherapp.geo_spring.service.IUserService;
import com.weatherapp.geo_spring.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner adminUserInitializer(IUserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userService.findUserByEmail("admin").isEmpty()) {

                User admin = new User();
                admin.setName("admin");
                admin.setEmail("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(Role.ROLE_ADMIN);

                userService.saveUser(admin);
                System.out.println("Admin user initialized: " + admin);
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
