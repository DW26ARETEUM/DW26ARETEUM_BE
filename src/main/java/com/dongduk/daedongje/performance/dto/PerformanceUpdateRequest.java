package com.dongduk.daedongje.performance.dto;

import com.dongduk.daedongje.performance.domain.PerformanceCategory;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

// 공연 수정 요청 (본문 전체를 다시 보내는 방식)
// 검증은 Service에서 처리 (팀 공통 예외 InvalidRequestException 사용)
public record PerformanceUpdateRequest(
        String title,
        String titleEn,
        String performer,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate performanceDate,
        @JsonFormat(pattern = "HH:mm") LocalTime startTime,
        @JsonFormat(pattern = "HH:mm") LocalTime endTime,
        String stage,
        PerformanceCategory category
) {
}