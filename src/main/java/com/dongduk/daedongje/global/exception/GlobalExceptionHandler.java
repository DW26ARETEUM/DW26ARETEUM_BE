package com.dongduk.daedongje.global.exception;

import com.dongduk.daedongje.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice // 모든 컨트롤러에서 올라온 예외를 여기서 받아 처리
public class GlobalExceptionHandler {

    private static final String INVALID_REQUEST_CODE = "COMMON_INVALID_REQUEST";

    // 우리가 직접 던진 400 (검색어 50자 초과, 찜 ID 100개 초과 등)
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidRequest(InvalidRequestException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(INVALID_REQUEST_CODE, e.getMessage()));
    }

    // 스프링이 던지는 400 (필수 파라미터 누락, 날짜 형식 오류, 없는 category, ids에 문자 포함)
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleBadParameter(Exception e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(INVALID_REQUEST_CODE, "잘못된 요청입니다."));
    }
}