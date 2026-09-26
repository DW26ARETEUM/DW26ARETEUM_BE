package com.dongduk.daedongje.performance.exception;

// 존재하지 않는 공연 (→ 404 PERFORMANCE_NOT_FOUND)
public class PerformanceNotFoundException extends RuntimeException {

    public PerformanceNotFoundException() {
        super("존재하지 않는 공연입니다.");
    }
}