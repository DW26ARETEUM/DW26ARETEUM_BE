package com.dongduk.daedongje.chat.controller;

import com.dongduk.daedongje.chat.dto.ChatMessageRequest;
import com.dongduk.daedongje.chat.dto.ChatMessageResponse;
import com.dongduk.daedongje.chat.service.ChatMessageService;
import com.dongduk.daedongje.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<ApiResponse<ChatMessageResponse>> saveMessage(
            @RequestHeader("X-Client-Id") String clientId,
            @RequestBody ChatMessageRequest request
    ) {
        ChatMessageResponse response =
                chatMessageService.saveMessage(clientId, request);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> getRecentMessages(
            @RequestParam(defaultValue = "50") int limit
    ) {
        List<ChatMessageResponse> response =
                chatMessageService.getRecentMessages(limit);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}