package com.example.websocket.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp; // Cần import này
// Thêm implements Serializable là tốt để đảm bảo tính tương thích, mặc dù Jackson không yêu cầu
import java.io.Serializable;
import java.time.LocalDateTime; // Cần import này
@Entity
@Table(name = "chat_message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessage implements Serializable { // Thêm Serializable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String sender;

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }
}