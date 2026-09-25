-- =========================================================
-- 부스 테이블 생성
-- 02_insert_booth_data.sql보다 먼저 실행해야 합니다.
-- =========================================================

USE dw26areteum;

-- 부스 기본 정보 (날짜가 바뀌어도 그대로인 정보)
CREATE TABLE IF NOT EXISTS booth (
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    name          VARCHAR(50) NOT NULL,            -- 부스명
    organizer     VARCHAR(50) NULL,                -- 운영 주체 (없으면 NULL)
    category      VARCHAR(20) NOT NULL,            -- 분류 (GENERAL, SOM_COLLECTION, COMMITTEE, FOOD_TRUCK, PUB)
    location_name VARCHAR(50) NOT NULL,            -- 장소
    PRIMARY KEY (id)
);

-- 날짜별 운영 정보 (날짜마다 달라지는 정보)
CREATE TABLE IF NOT EXISTS booth_operation (
    id             BIGINT NOT NULL AUTO_INCREMENT,
    booth_id       BIGINT NOT NULL,                -- 어느 부스의 운영 정보인지
    operation_date DATE   NOT NULL,                -- 운영 날짜
    start_time     TIME   NOT NULL,                -- 운영 시작 시간
    end_time       TIME   NOT NULL,                -- 운영 종료 시간
    map_number     INT    NOT NULL,                -- 그날 그 분류 안에서의 배치도 번호
    PRIMARY KEY (id),
    CONSTRAINT fk_booth_operation_booth
        FOREIGN KEY (booth_id) REFERENCES booth (id)
);