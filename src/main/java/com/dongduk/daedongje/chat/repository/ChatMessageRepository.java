package com.dongduk.daedongje.chat.repository;

import com.dongduk.daedongje.chat.domain.ChatCategory;
import com.dongduk.daedongje.chat.domain.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 최근 메시지 조회
    List<ChatMessage> findAllByOrderByMessageIdDesc(Pageable pageable);

    // 최근 메시지 조회 + 카테고리
    List<ChatMessage> findAllByCategoryOrderByMessageIdDesc(
            ChatCategory category,
            Pageable pageable
    );

    // 이전 메시지 조회
    List<ChatMessage> findAllByMessageIdLessThanOrderByMessageIdDesc(
            Long messageId,
            Pageable pageable
    );

    // 이전 메시지 조회 + 카테고리
    List<ChatMessage> findAllByCategoryAndMessageIdLessThanOrderByMessageIdDesc(
            ChatCategory category,
            Long messageId,
            Pageable pageable
    );

    // 이후 메시지 조회
    List<ChatMessage> findAllByMessageIdGreaterThanOrderByMessageIdAsc(
            Long messageId,
            Pageable pageable
    );

    // 이후 메시지 조회 + 카테고리
    List<ChatMessage> findAllByCategoryAndMessageIdGreaterThanOrderByMessageIdAsc(
            ChatCategory category,
            Long messageId,
            Pageable pageable
    );

    // 메시지 검색 (최신순)
    List<ChatMessage> findAllByContentContainingOrderByMessageIdDesc(
            String keyword,
            Pageable pageable
    );

    // 메시지 검색 + 카테고리 (최신순)
    List<ChatMessage> findAllByCategoryAndContentContainingOrderByMessageIdDesc(
            ChatCategory category,
            String keyword,
            Pageable pageable
    );
}
