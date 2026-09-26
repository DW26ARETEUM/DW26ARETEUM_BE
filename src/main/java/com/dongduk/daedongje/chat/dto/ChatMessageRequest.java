package com.dongduk.daedongje.chat.dto;

import com.dongduk.daedongje.chat.domain.ChatCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageRequest {

    private String content;

    private ChatCategory category;
}