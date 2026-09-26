USE dw26areteum;

-- 부스 상세 소개와 주점 아이콘 경로
ALTER TABLE booth
    ADD COLUMN description TEXT NULL
        COMMENT '부스 상세 소개',
    ADD COLUMN icon_image_path VARCHAR(255) NULL
        COMMENT '주점 아이콘 이미지 경로';

-- 날짜별 위치 이미지 경로
ALTER TABLE booth_operation
    ADD COLUMN location_image_path VARCHAR(255) NULL
        COMMENT '해당 운영 일정의 위치 이미지 경로';

-- 메뉴·상품 정보
CREATE TABLE booth_menu (
    id BIGINT NOT NULL AUTO_INCREMENT,
    booth_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NULL,
    price_text VARCHAR(255) NOT NULL,
    sort_order INT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_booth_menu_booth
        FOREIGN KEY (booth_id) REFERENCES booth (id),

    INDEX idx_booth_menu_order (booth_id, sort_order, id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;