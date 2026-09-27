package com.dongduk.daedongje.sse.event;

import com.dongduk.daedongje.sse.ChatSseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ChatSseEventListener {

    private final ChatSseService chatSseService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ChatMessageCreatedEvent event) {
        chatSseService.sendMessage(event.message());
    }
}
