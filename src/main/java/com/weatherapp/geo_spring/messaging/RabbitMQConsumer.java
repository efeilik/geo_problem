package com.weatherapp.geo_spring.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.geo_spring.model.Problem;
import com.weatherapp.geo_spring.model.User;
import com.weatherapp.geo_spring.service.IEmailService;
import com.weatherapp.geo_spring.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final IUserService userService;
    private final IEmailService emailService;

    @RabbitListener(queues = "mail-queue")
    public void receiveMessage(String message)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Problem problem = objectMapper.readValue(message, Problem.class);
            List<User> nearbyUsers = userService.findNearbyUsers(problem.getLatitude(), problem.getLongitude(), 5.0);

            emailService.sendEmailsForProblem(problem, nearbyUsers);
            System.out.println("Problem received: " + problem.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
