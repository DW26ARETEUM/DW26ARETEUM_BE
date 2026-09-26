package com.dongduk.daedongje.booth.exception;

// 요청한 부스가 존재하지 않을 때 발생시키는 예외
public class BoothNotFoundException extends RuntimeException {

    public BoothNotFoundException() {
        super("존재하지 않는 부스입니다.");
    }
}