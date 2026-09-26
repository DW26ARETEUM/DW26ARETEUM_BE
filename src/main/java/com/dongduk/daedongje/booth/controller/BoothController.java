package com.dongduk.daedongje.booth.controller;

import com.dongduk.daedongje.booth.domain.BoothCategory;
import com.dongduk.daedongje.booth.dto.BoothListResponse;
import com.dongduk.daedongje.booth.service.BoothService;
import com.dongduk.daedongje.global.response.ApiResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dongduk.daedongje.booth.dto.BoothDetailResponse;
import com.dongduk.daedongje.booth.service.BoothDetailService;
import org.springframework.web.bind.annotation.PathVariable;

@RestController // 메서드 반환값을 JSON으로 응답
@RequestMapping("/api/v1/booths") // 이 컨트롤러의 공통 주소
@RequiredArgsConstructor // 스프링이 BoothService를 넣어 줌
public class BoothController {

    private final BoothService boothService;
    private final BoothDetailService boothDetailService; // 부스 상세 조회 담당

    // 부스 목록 조회: GET /api/v1/booths?date=...&category=...&keyword=...&ids=...
    @GetMapping
    public ApiResponse<List<BoothListResponse>> getBooths(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) BoothCategory category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<Long> ids
    ) {
        return ApiResponse.success(boothService.getBooths(date, category, keyword, ids));
    }

    // 부스 상세 조회: GET /api/v1/booths/21
    @GetMapping("/{boothId}")
    public ApiResponse<BoothDetailResponse> getBoothDetail(
            @PathVariable("boothId") Long boothId
    ) {
        return ApiResponse.success(
                boothDetailService.getBoothDetail(boothId)
        );
    }
}