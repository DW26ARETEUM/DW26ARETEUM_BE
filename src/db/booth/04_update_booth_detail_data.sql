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

-- 축운위 위치 이미지 연결
-- 부스 ID 41~50 → 이미지 2_1.png~2_10.png
-- 29일·30일 모두 같은 이미지를 사용합니다.
UPDATE booth_operation o
    JOIN booth b ON b.id = o.booth_id
    SET o.location_image_path =
        CONCAT('/images/booth-locations/2_', b.id - 40, '.png')
WHERE b.category = 'COMMITTEE'
  AND b.id BETWEEN 41 AND 50
  AND o.operation_date IN ('2026-09-29', '2026-09-30');

-- 주점 위치 이미지 연결
-- 29일: 61→1, 62→2, 63→3, 64→4
-- 30일: 61→1, 62→2, 65→3, 66→4
UPDATE booth_operation o
    JOIN booth b ON b.id = o.booth_id
    SET o.location_image_path =
        CASE
        WHEN b.id = 61
        THEN '/images/booth-locations/3_1.png'
        WHEN b.id = 62
        THEN '/images/booth-locations/3_2.png'
        WHEN b.id IN (63, 65)
        THEN '/images/booth-locations/3_3.png'
        WHEN b.id IN (64, 66)
        THEN '/images/booth-locations/3_4.png'
END
WHERE b.category = 'PUB'
  AND (
      (o.operation_date = '2026-09-29'
       AND b.id IN (61, 62, 63, 64))
      OR
      (o.operation_date = '2026-09-30'
       AND b.id IN (61, 62, 65, 66))
  );

-- 주점 아이콘 연결
-- 부스 ID 61~66 → 아이콘 1.png~6.png
UPDATE booth
SET icon_image_path =
        CONCAT('/images/booth-icons/', id - 60, '.png')
WHERE category = 'PUB'
  AND id BETWEEN 61 AND 66;