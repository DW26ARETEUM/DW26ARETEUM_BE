package com.dongduk.daedongje.booth.domain;

// 부스 분류. 선언한 순서가 곧 목록 정렬 순서이므로 순서를 바꾸면 안 됨
public enum BoothCategory {
    GENERAL,        // 일반부스
    SOM_COLLECTION, // 솜컬렉션
    COMMITTEE,      // 축운위
    FOOD_TRUCK,     // 푸드트럭
    PUB             // 주점
}