package com.example.websocket.controller;

import com.example.websocket.config.RabbitMQConfig;
import com.example.websocket.model.ChatMessage;
import com.example.websocket.service.ChatMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class StompChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        ChatMessage saved = chatMessageService.saveMessage(chatMessage);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CHAT_QUEUE, chatMessage); // Gửi đến RabbitMQ
        return saved;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        ChatMessage saved = chatMessageService.saveMessage(chatMessage);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CHAT_QUEUE, chatMessage); // Gửi đến RabbitMQ
        return saved;
    }
}