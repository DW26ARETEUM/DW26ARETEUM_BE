package com.dongduk.daedongje.performance.exception;

import com.dongduk.daedongje.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 공연 파트 예외 처리
// 참고: 다른 파트에도 필요한 처리는 추후 global로 옮기는 것이 좋습니다.
@RestControllerAdvice
public class PerformanceExceptionHandler {

    private static final String NOT_FOUND_CODE = "PERFORMANCE_NOT_FOUND";
    private static final String INVALID_REQUEST_CODE = "COMMON_INVALID_REQUEST";

    // 존재하지 않는 공연
    @ExceptionHandler(PerformanceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(PerformanceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(NOT_FOUND_CODE, e.getMessage()));
    }

    // 요청 본문을 읽지 못한 경우 (날짜·시간 형식 오류, 없는 category 값 등)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotReadable(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(INVALID_REQUEST_CODE, "요청 형식이 올바르지 않습니다."));
    }
}