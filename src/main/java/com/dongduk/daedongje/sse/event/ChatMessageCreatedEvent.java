package com.dongduk.daedongje.sse.event;
import com.dongduk.daedongje.chat.dto.ChatMessageResponse;

public record ChatMessageCreatedEvent(
        ChatMessageResponse message
) {
}
