package com.dongduk.daedongje.performance.exception;

// 관리자 키가 없거나 일치하지 않음 (→ 403 COMMON_FORBIDDEN)
public class AdminKeyMismatchException extends RuntimeException {

    public AdminKeyMismatchException() {
        super("접근 권한이 없습니다.");
    }
}
