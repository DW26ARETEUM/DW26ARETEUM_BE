package com.dongduk.daedongje.performance.exception;

import com.dongduk.daedongje.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 공연 파트 예외 처리
// 참고: 부스·솜톡에도 404가 필요하면 global로 옮기는 것이 좋음
@RestControllerAdvice
public class PerformanceExceptionHandler {

    private static final String NOT_FOUND_CODE = "PERFORMANCE_NOT_FOUND";

    @ExceptionHandler(PerformanceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(PerformanceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(NOT_FOUND_CODE, e.getMessage()));
    }
}