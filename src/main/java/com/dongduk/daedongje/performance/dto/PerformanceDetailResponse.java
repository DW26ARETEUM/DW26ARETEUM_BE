package com.dongduk.daedongje.performance.dto;

import com.dongduk.daedongje.performance.domain.Performance;
import com.dongduk.daedongje.performance.domain.PerformanceCategory;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

// 공연 상세용 응답 (이미지는 프론트에서 id로 연결)
public record PerformanceDetailResponse(
        Long id,
        String title,
        String titleEn,
        String performer,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate performanceDate,
        @JsonFormat(pattern = "HH:mm") LocalTime startTime,
        @JsonFormat(pattern = "HH:mm") LocalTime endTime,
        String stage,
        PerformanceCategory category
) {
    public static PerformanceDetailResponse from(Performance p) {
        return new PerformanceDetailResponse(
                p.getId(), p.getTitle(), p.getTitleEn(), p.getPerformer(), p.getPerformanceDate(),
                p.getStartTime(), p.getEndTime(), p.getStage(), p.getCategory()
        );
    }
}