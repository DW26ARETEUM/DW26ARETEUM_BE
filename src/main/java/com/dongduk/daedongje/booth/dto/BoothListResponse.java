package com.dongduk.daedongje.booth.dto;

import com.dongduk.daedongje.booth.domain.Booth;
import com.dongduk.daedongje.booth.domain.BoothCategory;
import com.dongduk.daedongje.booth.domain.BoothOperation;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

// 부스 목록의 각 항목을 나타내는 응답
public record BoothListResponse(
        Long id,
        BoothCategory category,
        String name,
        String organizer,

        // 반환된 운영 시간과 지도 번호의 기준 날짜
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate operationDate,

        Integer mapNumber,
        @JsonFormat(pattern = "HH:mm") LocalTime startTime,
        @JsonFormat(pattern = "HH:mm") LocalTime endTime,
        String locationName
) {

    // 부스 기본 정보와 날짜별 운영 정보를 응답으로 변환
    public static BoothListResponse from(BoothOperation operation) {
        Booth booth = operation.getBooth();

        return new BoothListResponse(
                booth.getId(),
                booth.getCategory(),
                booth.getName(),
                booth.getOrganizer(),
                operation.getOperationDate(),
                operation.getMapNumber(),
                operation.getStartTime(),
                operation.getEndTime(),
                booth.getLocationName()
        );
    }
}