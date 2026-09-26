package com.dongduk.daedongje.chat.repository;

import com.dongduk.daedongje.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
