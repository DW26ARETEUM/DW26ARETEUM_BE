package com.dongduk.daedongje.chat.service;

import com.dongduk.daedongje.chat.domain.ChatCategory;
import com.dongduk.daedongje.chat.domain.ChatMessage;
import com.dongduk.daedongje.chat.dto.ChatMessageRequest;
import com.dongduk.daedongje.chat.dto.ChatMessageResponse;
import com.dongduk.daedongje.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessageResponse saveMessage(String clientId, ChatMessageRequest request) {
        ChatMessage chatMessage =
                new ChatMessage(clientId, request.getContent(), request.getCategory());

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        return ChatMessageResponse.builder()
                .messageId(savedMessage.getMessageId())
                .clientId(savedMessage.getClientId())
                .content(savedMessage.getContent())
                .category(savedMessage.getCategory())
                .createdAt(savedMessage.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getRecentMessages(int limit, ChatCategory category) {
        List<ChatMessage> messages;

        if (category == null) {
            messages = chatMessageRepository.findAllByOrderByMessageIdDesc(
                    PageRequest.of(0, limit)
            );
        } else {
            messages = chatMessageRepository.findAllByCategoryOrderByMessageIdDesc(
                    category,
                    PageRequest.of(0, limit)
            );
        }

        return messages.stream()
                .map(message -> ChatMessageResponse.builder()
                        .messageId(message.getMessageId())
                        .clientId(message.getClientId())
                        .content(message.getContent())
                        .category(message.getCategory())
                        .createdAt(message.getCreatedAt())
                        .build())
                .toList()
                .reversed();
    }
}