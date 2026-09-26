package com.dongduk.daedongje.performance.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "performance")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;              // 공연명(한글)

    @Column(length = 100)
    private String titleEn;            // 공연명(영문), 없으면 null

    @Column(nullable = false, length = 100)
    private String performer;          // 출연자(구분_이름)

    @Column(nullable = false)
    private LocalDate performanceDate; // 공연 날짜

    @Column(nullable = false)
    private LocalTime startTime;       // 시작 시간

    @Column(nullable = false)
    private LocalTime endTime;         // 종료 시간

    @Column(nullable = false, length = 50)
    private String stage;              // 장소

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(nullable = false, length = 20)
    private PerformanceCategory category;

    @Builder
    private Performance(String title, String titleEn, String performer,
                        LocalDate performanceDate, LocalTime startTime, LocalTime endTime,
                        String stage, PerformanceCategory category) {
        this.title = title;
        this.titleEn = titleEn;
        this.performer = performer;
        this.performanceDate = performanceDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stage = stage;
        this.category = category;
    }
}