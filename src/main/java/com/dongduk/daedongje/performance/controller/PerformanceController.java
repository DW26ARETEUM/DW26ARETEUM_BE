package com.dongduk.daedongje.performance.controller;

import com.dongduk.daedongje.global.response.ApiResponse;
import com.dongduk.daedongje.performance.dto.PerformanceDetailResponse;
import com.dongduk.daedongje.performance.dto.PerformanceSummaryResponse;
import com.dongduk.daedongje.performance.dto.PerformanceUpdateRequest;
import com.dongduk.daedongje.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    // 공연 목록 조회: GET /api/v1/performances?date=2026-09-29 (date 생략 시 전체)
    @GetMapping
    public ApiResponse<List<PerformanceSummaryResponse>> getPerformances(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ApiResponse.success(performanceService.getPerformances(date));
    }

    // 공연 상세 조회: GET /api/v1/performances/1
    @GetMapping("/{performanceId}")
    public ApiResponse<PerformanceDetailResponse> getPerformance(@PathVariable Long performanceId) {
        return ApiResponse.success(performanceService.getPerformance(performanceId));
    }

    // 공연 수정: PUT /api/v1/performances/1
    @PutMapping("/{performanceId}")
    public ApiResponse<PerformanceDetailResponse> updatePerformance(
            @PathVariable Long performanceId,
            @RequestBody PerformanceUpdateRequest request
    ) {
        return ApiResponse.success(performanceService.updatePerformance(performanceId, request));
    }

    // 공연 삭제: DELETE /api/v1/performances/1
    @DeleteMapping("/{performanceId}")
    public ApiResponse<Void> deletePerformance(@PathVariable Long performanceId) {
        performanceService.deletePerformance(performanceId);
        return ApiResponse.success(null);
    }
}