package com.dongduk.daedongje.chat.repository;

import com.dongduk.daedongje.chat.domain.ChatCategory;
import com.dongduk.daedongje.chat.domain.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByOrderByMessageIdDesc(Pageable pageable);

    List<ChatMessage> findAllByCategoryOrderByMessageIdDesc(
            ChatCategory category,
            Pageable pageable
    );

    List<ChatMessage> findAllByMessageIdLessThanOrderByMessageIdDesc(
            Long messageId,
            Pageable pageable
    );

    List<ChatMessage> findAllByCategoryAndMessageIdLessThanOrderByMessageIdDesc(
            ChatCategory category,
            Long messageId,
            Pageable pageable
    );

    List<ChatMessage> findAllByMessageIdGreaterThanOrderByMessageIdAsc(
            Long messageId,
            Pageable pageable
    );

    List<ChatMessage> findAllByCategoryAndMessageIdGreaterThanOrderByMessageIdAsc(
            ChatCategory category,
            Long messageId,
            Pageable pageable
    );
}