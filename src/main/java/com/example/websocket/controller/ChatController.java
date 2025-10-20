package com.example.websocket.controller;

import com.example.websocket.config.RabbitMQConfig;
import com.example.websocket.model.ChatMessage;
import com.example.websocket.service.ChatMessageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @PostMapping("/sendMessage")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage chatMessage) {
        System.out.println("Received REST message: " + chatMessage);
        ChatMessage saved = chatMessageService.saveMessage(chatMessage);
        System.out.println("Saved REST message: " + saved);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/addUser")
    public ResponseEntity<ChatMessage> addUser(@RequestBody ChatMessage chatMessage) {
        System.out.println("Received REST user join: " + chatMessage);
        ChatMessage saved = chatMessageService.saveMessage(chatMessage);
        return ResponseEntity.ok(saved);
    }

    @RabbitListener(queues = RabbitMQConfig.CHAT_QUEUE)
    public void receiveMessage(ChatMessage message) {
        chatMessageService.saveMessage(message);
        System.out.println("💾 Đã lưu message từ RabbitMQ: " + message.getContent());
    }
}