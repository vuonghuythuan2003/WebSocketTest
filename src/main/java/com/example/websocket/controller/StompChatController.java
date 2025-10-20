package com.example.websocket.controller;// Trong StompChatController.java

import com.example.websocket.model.ChatMessage;
import com.example.websocket.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class StompChatController {

    @Autowired
    private ChatMessageService chatMessageService; // THÊM DÒNG NÀY

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {

        // 1. LƯU VÀO DB (BẮT BUỘC)
        chatMessageService.saveMessage(chatMessage);

        // 2. Phát sóng lại
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        // 3. LƯU SỰ KIỆN JOIN VÀO DB (BẮT BUỘC)
        chatMessage.setType(ChatMessage.MessageType.JOIN); // Đảm bảo type là JOIN
        chatMessageService.saveMessage(chatMessage);

        return chatMessage;
    }
}