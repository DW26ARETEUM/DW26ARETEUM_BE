package com.dongduk.daedongje.chat.service;

import com.dongduk.daedongje.chat.domain.ChatMessage;
import com.dongduk.daedongje.chat.dto.ChatMessageRequest;
import com.dongduk.daedongje.chat.dto.ChatMessageResponse;
import com.dongduk.daedongje.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessageResponse saveMessage(String clientId, ChatMessageRequest request) {
        ChatMessage chatMessage =
                new ChatMessage(clientId, request.getContent());

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        return ChatMessageResponse.builder()
                .messageId(savedMessage.getMessageId())
                .clientId(savedMessage.getClientId())
                .content(savedMessage.getContent())
                .createdAt(savedMessage.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getRecentMessages(int limit) {
        List<ChatMessage> messages =
                chatMessageRepository.findAllByOrderByMessageIdDesc(
                        PageRequest.of(0, limit)
                );

        return messages.stream()
                .map(message -> ChatMessageResponse.builder()
                        .messageId(message.getMessageId())
                        .clientId(message.getClientId())
                        .content(message.getContent())
                        .createdAt(message.getCreatedAt())
                        .build())
                .toList()
                .reversed();
    }
}