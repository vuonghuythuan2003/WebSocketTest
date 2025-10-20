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
        try {
            System.out.println("Saving message to DB: " + chatMessage);
            ChatMessage saved = chatMessageRepository.save(chatMessage);
            System.out.println("Saved to DB: " + saved);
            return saved;
        } catch (Exception e) {
            System.err.println("Error saving message: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}