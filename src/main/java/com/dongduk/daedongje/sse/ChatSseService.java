package com.dongduk.daedongje.sse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;

@Service
public class ChatSseService {

    private static final long SSE_TIMEOUT = 60L * 60 * 1000; // 1시간

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe() {
        String emitterId = UUID.randomUUID().toString();

        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
        emitters.put(emitterId, emitter);

        emitter.onCompletion(() -> emitters.remove(emitterId));
        emitter.onTimeout(() -> emitters.remove(emitterId));
        emitter.onError(error -> emitters.remove(emitterId));

        // 새 메세지 없는 동안 connect 확인용 예외코드
        try {
            emitter.send(
                    SseEmitter.event()
                            .name("connect")
                            .data("connected")
            );
        } catch (IOException e) {
            emitters.remove(emitterId);
        }

        return emitter;
    }
}
