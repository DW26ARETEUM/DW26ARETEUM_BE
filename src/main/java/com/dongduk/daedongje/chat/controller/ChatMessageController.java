package com.dongduk.daedongje.chat.controller;

import com.dongduk.daedongje.chat.domain.ChatCategory;
import com.dongduk.daedongje.chat.dto.ChatMessageRequest;
import com.dongduk.daedongje.chat.dto.ChatMessageResponse;
import com.dongduk.daedongje.chat.service.ChatMessageService;
import com.dongduk.daedongje.global.exception.InvalidRequestException;
import com.dongduk.daedongje.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    private boolean isValidUuidV4(String clientId) {
        try {
            UUID uuid = UUID.fromString(clientId);
            return uuid.version() == 4;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ChatMessageResponse>> saveMessage(
            @RequestHeader("X-Client-Id") String clientId,
            @RequestBody ChatMessageRequest request
    ) {
        if (!isValidUuidV4(clientId)) {
            throw new InvalidRequestException("유효하지 않은 clientId입니다.");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new InvalidRequestException("메시지는 비어 있을 수 없습니다.");
        }

        if (request.getContent().length() > 53) {
            throw new InvalidRequestException("메시지는 53자를 초과할 수 없습니다.");
        }

        if (request.getCategory() == null || request.getCategory().isBlank()) {
            throw new InvalidRequestException("카테고리는 필수입니다.");
        }

        ChatCategory category;

        try {
            category = ChatCategory.valueOf(request.getCategory());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("유효하지 않은 카테고리입니다.");
        }

        ChatMessageResponse response =
                chatMessageService.saveMessage(clientId, request, category);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> getRecentMessages(
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(required = false) ChatCategory category
    ) {
        if (limit <= 0 || limit > 100) {
            throw new InvalidRequestException("limit은 1 이상 100 이하이어야 합니다.");
        }

        List<ChatMessageResponse> response =
                chatMessageService.getRecentMessages(limit, category);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}