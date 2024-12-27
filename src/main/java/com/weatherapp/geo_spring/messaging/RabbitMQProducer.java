package com.weatherapp.geo_spring.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.geo_spring.model.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(Problem problem)
    {
        try {
            String message = objectMapper.writeValueAsString(problem);

            rabbitTemplate.convertAndSend(
                    "", "mail-queue", message
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
