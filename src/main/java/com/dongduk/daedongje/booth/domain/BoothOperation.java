package com.dongduk.daedongje.booth.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoothOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // 운영 정보 여러 개 → 부스 하나 (N:1)
    @JoinColumn(name = "booth_id") // DB에서는 booth_id 컬럼으로 부스를 가리킴
    private Booth booth;

    @Column(nullable = false)
    private LocalDate operationDate; // 운영 날짜 (2026-09-29)

    @Column(nullable = false)
    private LocalTime startTime; // 운영 시작 시간

    @Column(nullable = false)
    private LocalTime endTime; // 운영 종료 시간

    @Column(nullable = false)
    private Integer mapNumber; // 그날 그 분류 안에서의 배치도 칸 번호
}
