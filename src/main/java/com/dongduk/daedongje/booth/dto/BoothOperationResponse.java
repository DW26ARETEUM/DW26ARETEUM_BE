package com.dongduk.daedongje.booth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public record BoothOperationResponse(
        LocalDate operationDate, // 실제 운영 날짜

        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime, // 운영 시작 시간

        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime, // 운영 종료 시간

        Integer mapNumber, // 해당 날짜·분류의 지도 번호
        String locationImageUrl // 위치 상세 이미지 주소
) {
}