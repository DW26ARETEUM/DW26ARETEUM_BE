package com.dongduk.daedongje.chat.dto;

import com.dongduk.daedongje.chat.domain.ChatCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponse {

    private Long messageId;
    private String clientId;
    private String content;
    private ChatCategory category;
    private LocalDateTime createdAt;
}