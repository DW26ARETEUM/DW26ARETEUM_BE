package com.dongduk.daedongje.performance.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 공연 수정·삭제 API에만 관리자 키 확인을 적용
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AdminKeyInterceptor adminKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminKeyInterceptor)
                .addPathPatterns("/api/v1/performances/*")
                .order(1);
    }
}