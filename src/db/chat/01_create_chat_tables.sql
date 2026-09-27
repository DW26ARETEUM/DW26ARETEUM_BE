-- =========================================================
-- 채팅 메시지 테이블 생성
-- =========================================================

USE dw26areteum;

-- 기존 테이블이 있으면 삭제
DROP TABLE IF EXISTS chat_message;

-- 익명 채팅 메시지 정보
CREATE TABLE chat_message (
      message_id BIGINT       NOT NULL AUTO_INCREMENT,
      client_id  VARCHAR(36)  NOT NULL,                -- 익명 사용자 식별자
      content    VARCHAR(53)  NOT NULL,                -- 채팅 메시지 내용
      category   VARCHAR(10)  NOT NULL,                -- 채팅 카테고리 (CHAT / INFO)
      created_at DATETIME     NOT NULL,                -- 메시지 생성 시간
      PRIMARY KEY (message_id)
);