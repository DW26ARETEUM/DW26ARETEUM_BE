package com.dongduk.daedongje.performance.service;

import com.dongduk.daedongje.global.exception.InvalidRequestException;
import com.dongduk.daedongje.performance.domain.Performance;
import com.dongduk.daedongje.performance.dto.PerformanceDetailResponse;
import com.dongduk.daedongje.performance.dto.PerformanceSummaryResponse;
import com.dongduk.daedongje.performance.dto.PerformanceUpdateRequest;
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
        return PerformanceDetailResponse.from(findPerformance(id));
    }

    // 공연 수정 (본문 전체 교체)
    @Transactional
    public PerformanceDetailResponse updatePerformance(Long id, PerformanceUpdateRequest request) {
        validate(request);

        Performance performance = findPerformance(id);
        performance.update(
                request.title(), request.titleEn(), request.performer(),
                request.performanceDate(), request.startTime(), request.endTime(),
                request.stage(), request.category()
        );
        return PerformanceDetailResponse.from(performance);
    }

    // 공연 삭제
    @Transactional
    public void deletePerformance(Long id) {
        performanceRepository.delete(findPerformance(id));
    }

    private Performance findPerformance(Long id) {
        return performanceRepository.findById(id)
                .orElseThrow(PerformanceNotFoundException::new);
    }

    // 요청값 검증 (400 COMMON_INVALID_REQUEST)
    private void validate(PerformanceUpdateRequest request) {
        if (isBlank(request.title())) {
            throw new InvalidRequestException("공연명은 필수입니다.");
        }
        if (isBlank(request.performer())) {
            throw new InvalidRequestException("출연자는 필수입니다.");
        }
        if (isBlank(request.stage())) {
            throw new InvalidRequestException("장소는 필수입니다.");
        }
        if (request.performanceDate() == null) {
            throw new InvalidRequestException("공연 날짜는 필수입니다.");
        }
        if (request.startTime() == null || request.endTime() == null) {
            throw new InvalidRequestException("공연 시간은 필수입니다.");
        }
        if (request.category() == null) {
            throw new InvalidRequestException("공연 구분은 필수입니다.");
        }
        if (!request.endTime().isAfter(request.startTime())) {
            throw new InvalidRequestException("종료 시간은 시작 시간보다 늦어야 합니다.");
        }
        if (request.title().length() > 100 || request.performer().length() > 100) {
            throw new InvalidRequestException("공연명과 출연자는 100자 이하여야 합니다.");
        }
        if (request.titleEn() != null && request.titleEn().length() > 100) {
            throw new InvalidRequestException("영문 공연명은 100자 이하여야 합니다.");
        }
        if (request.stage().length() > 50) {
            throw new InvalidRequestException("장소는 50자 이하여야 합니다.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}