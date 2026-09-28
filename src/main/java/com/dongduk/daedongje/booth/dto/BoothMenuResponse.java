package com.dongduk.daedongje.booth.dto;

import com.dongduk.daedongje.booth.domain.BoothMenu;

public record BoothMenuResponse(
        Long id, // 메뉴·상품 ID
        String name, // 메뉴·상품명
        String description, // 추가 설명. 없으면 null
        String priceText // 표시용 가격 문자열
) {

    // 메뉴 Entity를 프론트에 전달할 응답으로 변환
    public static BoothMenuResponse from(BoothMenu menu) {
        return new BoothMenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPriceText()
        );
    }
}