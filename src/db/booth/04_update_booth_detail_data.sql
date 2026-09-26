USE dw26areteum;

-- 부스 상세 데이터 입력
-- 03_add_booth_detail_schema.sql 적용 후 실행합니다.
-- 부스 기본 데이터와 운영 일정이 먼저 입력되어 있어야 합니다.

-- 달팽이잡화점(id: 21)의 위치 이미지
-- 양일 모두 솜컬렉션 지도 번호 1번이며,
-- 지도 번호 1·2번은 같은 위치 이미지(1_1.png)를 사용합니다.
UPDATE booth_operation
SET location_image_path = '/images/booth-locations/1_1.png'
WHERE booth_id = 21
  AND operation_date IN ('2026-09-29', '2026-09-30');