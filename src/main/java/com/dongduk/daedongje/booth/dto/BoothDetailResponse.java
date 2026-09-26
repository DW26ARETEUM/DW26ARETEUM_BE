package com.dongduk.daedongje.booth.dto;

import com.dongduk.daedongje.booth.domain.BoothCategory;
import java.util.List;

public record BoothDetailResponse(
        Long id, // 부스 ID
        BoothCategory category, // 부스 분류
        String name, // 부스명
        String organizer, // 운영 주체. 없으면 null
        String locationName, // 장소
        String description, // 부스 소개. 없으면 null
        String iconImageUrl, // 주점 아이콘 주소. 없으면 null
        List<BoothOperationResponse> operations, // 전체 운영 일정
        List<BoothMenuResponse> menus // 메뉴·상품 목록
) {
}