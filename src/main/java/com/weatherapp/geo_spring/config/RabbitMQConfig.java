package com.weatherapp.geo_spring.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue()
    {
        return new Queue("mail-queue", true);
    }

}
