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

롬-- 축운위 위치 이미지 연결
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

-- 일반부스 위치 이미지 연결
-- 날짜별 지도 번호 1~14 → 이미지 4_1.png~4_14.png
UPDATE booth_operation o
    JOIN booth b ON b.id = o.booth_id
    SET o.location_image_path =
        CONCAT('/images/booth-locations/4_', o.map_number, '.png')
WHERE b.category = 'GENERAL'
  AND o.map_number BETWEEN 1 AND 14
  AND o.operation_date IN ('2026-09-29', '2026-09-30');

-- 푸드트럭 위치 이미지 연결
-- 날짜별 지도 번호 1~6 → 이미지 5_1.png~5_6.png
UPDATE booth_operation o
    JOIN booth b ON b.id = o.booth_id
    SET o.location_image_path =
        CONCAT('/images/booth-locations/5_', o.map_number, '.png')
WHERE b.category = 'FOOD_TRUCK'
  AND o.map_number BETWEEN 1 AND 6
  AND o.operation_date IN ('2026-09-29', '2026-09-30');


-- 축운위 부스 소개
-- 부스명은 유지하고 상세 소개만 입력합니다.
UPDATE booth
SET description =
        CASE id
            WHEN 41 THEN
                '인포메이션 부스에서 체크인 카드를 발급받고, 각 부스를 체험하며 스탬프를 모아보세요. 5칸을 완성하면 특별한 상품이 기다리고 있습니다.'

            WHEN 42 THEN
                '물품제공 등 일반부스, 플리마켓 운영자를 관리하는 부스입니다.'

            WHEN 43 THEN
                '솜솜과 소품샵의 만남! 굿즈 판매와 아크릴 키링 증정, 포토부스까지 준비되어 있어 추억을 남기고 특별한 아이템을 얻을 수 있습니다.'

            WHEN 44 THEN
                '포토부스까지 준비되어 있어 추억을 남기고 특별한 아이템을 얻을 수 있습니다.'

            WHEN 45 THEN
                '1일차: 대도시의 사랑법 / 2일차: 만약에 우리. 야외에서 펼쳐지는 특별한 영화 상영! 빈백에 앉아 편안하게 영화를 즐길 수 있으며, 티켓 배부와 관람평 이벤트를 통해 다양한 상품도 받을 수 있습니다. 영화가 끝난 뒤에는 메인 공연이 이어져 더욱 풍성한 밤을 선사합니다.'

            WHEN 46 THEN
                '푸드존에서 다양한 음식을 즐길 수 있는 공간! 테이블과 의자가 마련되어 편하게 식사할 수 있으며, 텐트 대여도 가능해 자유로운 분위기를 만끽할 수 있습니다.'

            WHEN 47 THEN
                '"오늘 밤 나는 가장 운이 좋은 사람이다!" Lucky Girl Syndrome을 테마로 한 이벤트. 네잎클로버 포토카드를 찾은 만큼 기준에 따라 상품을 증정하며, 참가자들에게 특별한 행운의 경험을 선사합니다.'

            WHEN 48 THEN
                '걸스나잇 컨셉에 맞춘 우정 팔찌 만들기! 제한시간 동안 젓가락으로 비즈를 옮겨 팔찌를 완성하고, 포토존에서 사진을 찍고 인증하면 상품까지 증정됩니다.'

            WHEN 49 THEN
                '스릴 넘치는 인간 룰렛! 세 명의 진행자가 랜덤으로 물건을 제시하고 결과에 따라 상품을 증정합니다. 세 가지가 모두 같으면 잭팟 당첨! 티켓도 제공되어 누구나 즐길 수 있는 이벤트입니다.'

            WHEN 50 THEN
                '주점부스 운영자를 관리하는 부스입니다.'
            END
WHERE category = 'COMMITTEE'
  AND id BETWEEN 41 AND 50;