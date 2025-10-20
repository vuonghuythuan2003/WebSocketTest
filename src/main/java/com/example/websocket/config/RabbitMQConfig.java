package com.example.websocket.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String CHAT_QUEUE = "chat-queue";

    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE, true); // Queue bền vững
    }
}