package com.dongduk.daedongje.performance.config;

import com.dongduk.daedongje.performance.exception.AdminKeyMismatchException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// 공연 수정·삭제 요청에 관리자 키가 있는지 확인
@Component
public class AdminKeyInterceptor implements HandlerInterceptor {

    private static final String ADMIN_KEY_HEADER = "X-Admin-Key";

    @Value("${admin.key}")
    private String adminKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 조회(GET)는 인증 없이 허용
        if (HttpMethod.GET.matches(request.getMethod())) {
            return true;
        }

        String requestKey = request.getHeader(ADMIN_KEY_HEADER);

        if (requestKey == null || !requestKey.equals(adminKey)) {
            throw new AdminKeyMismatchException();
        }
        return true;
    }
}