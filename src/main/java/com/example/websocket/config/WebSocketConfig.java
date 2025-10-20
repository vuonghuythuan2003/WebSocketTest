package com.example.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");

        // SỬA: Dùng STOMP Broker Relay để kết nối với RabbitMQ (chạy trên cổng 61613 mặc định)
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("localhost") // Thay bằng IP của RabbitMQ nếu không chạy local
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
    }

    // -----------------------------------------------------------
    // KHẮC PHỤC SimpleMessageConverter (JSON Serialization)
    // -----------------------------------------------------------

    // 1. Khai báo Bean Message Converter (Jackson)
    @Bean
    public MessageConverter messageConverter() {
        return new MappingJackson2MessageConverter();
    }

    // 2. Ghi đè phương thức để áp dụng Jackson Converter
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add(messageConverter());
        // Trả về true để bảo Spring chỉ sử dụng các converters đã cấu hình (loại bỏ SimpleMessageConverter)
        return true;
    }
}