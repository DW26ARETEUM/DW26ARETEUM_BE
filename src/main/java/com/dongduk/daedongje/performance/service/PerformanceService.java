package com.dongduk.daedongje.performance.service;

import com.dongduk.daedongje.performance.dto.PerformanceDetailResponse;
import com.dongduk.daedongje.performance.dto.PerformanceSummaryResponse;
import com.dongduk.daedongje.performance.exception.PerformanceNotFoundException;
import com.dongduk.daedongje.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    // 날짜가 있으면 그 날짜만, 없으면 전체 공연 목록
    public List<PerformanceSummaryResponse> getPerformances(LocalDate date) {
        var performances = (date == null)
                ? performanceRepository.findAllByOrderByPerformanceDateAscStartTimeAsc()
                : performanceRepository.findByPerformanceDateOrderByStartTimeAsc(date);

        return performances.stream()
                .map(PerformanceSummaryResponse::from)
                .toList();
    }

    // 공연 상세
    public PerformanceDetailResponse getPerformance(Long id) {
        return performanceRepository.findById(id)
                .map(PerformanceDetailResponse::from)
                .orElseThrow(PerformanceNotFoundException::new);
    }
}