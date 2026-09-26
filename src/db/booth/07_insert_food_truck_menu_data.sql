USE dw26areteum;

-- 푸드트럭 메뉴 데이터
-- 기존 부스 기본 데이터와 booth_menu 테이블이 필요합니다.
-- 51~56번 푸드트럭의 메뉴만 교체합니다.
-- 양일 메뉴가 동일하므로 부스마다 한 번씩 저장합니다.
-- 가격 범위는 디자인 기준으로 유지합니다.
-- 재실행하면 메뉴 ID는 달라질 수 있습니다.

START TRANSACTION;

DELETE FROM booth_menu
WHERE booth_id IN (51, 52, 53, 54, 55, 56);

INSERT INTO booth_menu
(booth_id, name, description, price_text, sort_order)
VALUES

-- 51: 커피스토리로드카페
(51, '커피류', NULL, '4,000~5,000₩', 1),
(51, '라떼류', NULL, '4,500~5,500₩', 2),
(51, '에이드', NULL, '5,000~6,000₩', 3),
(51, '논알콜칵테일', NULL, '5,000~6,000₩', 4),
(51, '생과일주스', NULL, '5,000~6,000₩', 5),

-- 52: 와이
(52, '야끼소바', NULL, '10,000₩', 1),
(52, '오꼬노미야끼', NULL, '10,000₩', 2),
(52, '타코야끼', NULL, '5,000₩', 3),

-- 53: 쏘굿
(53, '크레페', NULL, '7,000₩', 1),
(53, '아이스크림크레페', NULL, '8,000₩', 2),

-- 54: 부엉이푸드
(54, '불초밥', NULL, '12,000₩', 1),
(54, '연어초밥', NULL, '13,000₩', 2),

-- 55: 메리푸드
(55, '떡볶이', NULL, '5,000₩', 1),
(55, '순대', NULL, '5,000₩', 2),
(55, '가래떡꼬치(2p)', NULL, '5,000₩', 3),
(55, '떡튀순세트', NULL, '12,000₩', 4),

-- 56: 다온푸드
(56, '크림/칠리새우 (중)', NULL, '10,000₩', 1),
(56, '크림/칠리새우 (대)', NULL, '16,000₩', 2),
(56, '반반새우', NULL, '16,000₩', 3);

COMMIT;

-- 부스별 메뉴 개수 확인
SELECT
    b.id AS booth_id,
    b.name,
    COUNT(m.id) AS menu_count
FROM booth b
         LEFT JOIN booth_menu m ON m.booth_id = b.id
WHERE b.category = 'FOOD_TRUCK'
  AND b.id IN (51, 52, 53, 54, 55, 56)
GROUP BY b.id, b.name
ORDER BY b.id;