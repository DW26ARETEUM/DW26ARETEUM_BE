package com.dongduk.daedongje.booth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booth_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoothMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이 메뉴가 속한 부스
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;

    // 메뉴·상품 이름
    @Column(nullable = false, length = 255)
    private String name;

    // 추가 설명 또는 세트 구성
    @Column(columnDefinition = "TEXT")
    private String description;

    // 예: "3000₩~", "4000~5000₩"
    @Column(nullable = false, length = 255)
    private String priceText;

    // 화면에 표시할 순서
    @Column(nullable = false)
    private Integer sortOrder;
}