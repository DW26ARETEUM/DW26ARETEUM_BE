USE dw26areteum;

-- =============================================
-- 공연 테이블 생성
-- =============================================

CREATE TABLE IF NOT EXISTS performance (
                                           id               BIGINT       NOT NULL AUTO_INCREMENT,
                                           title            VARCHAR(100) NOT NULL COMMENT '공연명(한글)',
    title_en         VARCHAR(100) NULL     COMMENT '공연명(영문), 없으면 NULL',
    performer        VARCHAR(100) NOT NULL COMMENT '출연자(구분_이름)',
    performance_date DATE         NOT NULL COMMENT '공연 날짜',
    start_time       TIME         NOT NULL COMMENT '시작 시간',
    end_time         TIME         NOT NULL COMMENT '종료 시간',
    stage            VARCHAR(50)  NOT NULL COMMENT '장소',
    category         VARCHAR(20)  NOT NULL COMMENT '공연 구분(CLUB/GENERAL/SPECIAL/ARTIST)',
    PRIMARY KEY (id),
    KEY idx_performance_date_start_time (performance_date, start_time)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '공연 정보';