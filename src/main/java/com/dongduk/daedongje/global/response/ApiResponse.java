package com.dongduk.daedongje.global.response;

// 팀 공통 응답 형식 { success, code, message, data }
public record ApiResponse<T>(boolean success, String code, String message, T data) {

    // 성공 응답 생성 (data에 실제 결과를 담음)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "SUCCESS", "요청에 성공했습니다.", data);
    }

    // 실패 응답 생성 (data는 항상 null)
    public static ApiResponse<Void> fail(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}