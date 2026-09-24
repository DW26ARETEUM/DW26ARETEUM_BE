package com.dongduk.daedongje.global.exception;

// 요청값이 잘못됐을 때 던지는 예외 (→ 400 COMMON_INVALID_REQUEST)
public class InvalidRequestException extends RuntimeException {

    // 오류 원인 메시지를 받아서 생성
    public InvalidRequestException(String message) {
        super(message);
    }
}