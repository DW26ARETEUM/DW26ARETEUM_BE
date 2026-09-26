package com.dongduk.daedongje.chat.controller;

import com.dongduk.daedongje.chat.dto.ChatMessageRequest;
import com.dongduk.daedongje.chat.dto.ChatMessageResponse;
import com.dongduk.daedongje.chat.service.ChatMessageService;
import com.dongduk.daedongje.global.exception.InvalidRequestException;
import com.dongduk.daedongje.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.util.List;

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
            @Valid @RequestBody ChatMessageRequest request
    ) {
        if (!isValidUuidV4(clientId)) {
            throw new InvalidRequestException("유효하지 않은 clientId입니다.");
        }

        ChatMessageResponse response =
                chatMessageService.saveMessage(clientId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> getRecentMessages(
            @RequestParam(defaultValue = "50") int limit
    ) {
        if (limit <= 0) {
            throw new InvalidRequestException("limit은 1 이상이어야 합니다.");
        }

        List<ChatMessageResponse> response =
                chatMessageService.getRecentMessages(limit);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}