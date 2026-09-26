USE dw26areteum;

-- 부스 상세 데이터 입력
-- 03_add_booth_detail_schema.sql 적용 후 실행합니다.
-- 부스 기본 데이터와 운영 일정이 먼저 입력되어 있어야 합니다.

-- 솜컬렉션 위치 이미지 연결
-- 부스 ID를 기준으로 전달받은 이미지 파일과 연결합니다.
-- 양일 운영하는 부스는 두 날짜에 같은 이미지를 사용합니다.
UPDATE booth_operation o
    JOIN booth b ON b.id = o.booth_id
    SET o.location_image_path =
        CASE
        WHEN b.id IN (21, 22)
        THEN '/images/booth-locations/1_1.png'
        WHEN b.id IN (23, 24, 32)
        THEN '/images/booth-locations/1_2.png'
        WHEN b.id IN (25, 26, 27)
        THEN '/images/booth-locations/1_3.png'
        WHEN b.id IN (28, 29)
        THEN '/images/booth-locations/1_4.png'
        WHEN b.id IN (30, 31, 33)
        THEN '/images/booth-locations/1_5.png'
END
WHERE b.category = 'SOM_COLLECTION'
  AND b.id IN (
      21, 22, 23, 24, 25, 26, 27,
      28, 29, 30, 31, 32, 33
  )
  AND o.operation_date IN ('2026-09-29', '2026-09-30');

