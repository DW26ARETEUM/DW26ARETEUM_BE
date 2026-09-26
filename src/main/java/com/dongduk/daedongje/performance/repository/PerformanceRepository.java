package com.dongduk.daedongje.performance.repository;

import com.dongduk.daedongje.performance.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    // 특정 날짜 공연을 시작 시간 순으로 조회
    List<Performance> findByPerformanceDateOrderByStartTimeAsc(LocalDate performanceDate);

    // 전체 공연을 날짜 → 시작 시간 순으로 조회
    List<Performance> findAllByOrderByPerformanceDateAscStartTimeAsc();
}