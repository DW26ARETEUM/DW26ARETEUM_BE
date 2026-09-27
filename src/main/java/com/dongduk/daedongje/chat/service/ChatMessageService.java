package com.dongduk.daedongje.chat.service;

import com.dongduk.daedongje.sse.event.ChatMessageCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;


import com.dongduk.daedongje.chat.domain.ChatCategory;
import com.dongduk.daedongje.chat.domain.ChatMessage;
import com.dongduk.daedongje.chat.dto.ChatMessageRequest;
import com.dongduk.daedongje.chat.dto.ChatMessageResponse;
import com.dongduk.daedongje.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ChatMessageResponse saveMessage(String clientId, ChatMessageRequest request, ChatCategory category) {
        ChatMessage chatMessage =
                new ChatMessage(clientId, request.getContent(), category);

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        ChatMessageResponse response = ChatMessageResponse.builder()
                .messageId(savedMessage.getMessageId())
                .clientId(savedMessage.getClientId())
                .content(savedMessage.getContent())
                .category(savedMessage.getCategory())
                .createdAt(savedMessage.getCreatedAt())
                .build();

        eventPublisher.publishEvent(
                new ChatMessageCreatedEvent(response)
        );
        return response;
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

    // 이전 메세지 페이징 조회
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getPreviousMessages(
            Long before,
            int limit,
            ChatCategory category
    ) {
        Pageable pageable = PageRequest.of(0, limit);

        List<ChatMessage> messages;

        if (category == null) {
            messages = chatMessageRepository
                    .findAllByMessageIdLessThanOrderByMessageIdDesc(before, pageable);
        } else {
            messages = chatMessageRepository
                    .findAllByCategoryAndMessageIdLessThanOrderByMessageIdDesc(
                            category,
                            before,
                            pageable
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

    // 이후 메세지 페이징 조회
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getAfterMessages(
            Long after,
            int limit,
            ChatCategory category
    ) {
        Pageable pageable = PageRequest.of(0, limit);

        List<ChatMessage> messages;

        if (category == null) {
            messages = chatMessageRepository
                    .findAllByMessageIdGreaterThanOrderByMessageIdAsc(after, pageable);
        } else {
            messages = chatMessageRepository
                    .findAllByCategoryAndMessageIdGreaterThanOrderByMessageIdAsc(
                            category,
                            after,
                            pageable
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
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> searchMessages(
            String keyword,
            int limit,
            ChatCategory category
    ) {
        Pageable pageable = PageRequest.of(0, limit);

        List<ChatMessage> messages;

        if (category == null) {
            messages = chatMessageRepository
                    .findAllByContentContainingOrderByMessageIdAsc(
                            keyword,
                            pageable
                    );
        } else {
            messages = chatMessageRepository
                    .findAllByCategoryAndContentContainingOrderByMessageIdAsc(
                            category,
                            keyword,
                            pageable
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
                .toList();
    }
}