package com.example.websocket.service;

import com.example.websocket.model.ChatMessage;
import com.example.websocket.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessage saveMessage(ChatMessage chatMessage) {
        // Logic lưu vào DB
        return chatMessageRepository.save(chatMessage);
    }
}