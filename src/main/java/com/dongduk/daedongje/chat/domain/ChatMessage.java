package com.dongduk.daedongje.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false, length = 36)
    private String clientId;

    @Column(nullable = false, length = 53)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ChatCategory category;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ChatMessage(String clientId, String content, ChatCategory category) {
        this.clientId = clientId;
        this.content = content;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }
}