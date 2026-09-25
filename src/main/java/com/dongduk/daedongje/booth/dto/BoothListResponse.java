package com.dongduk.daedongje.booth.dto;

import com.dongduk.daedongje.booth.domain.Booth;
import com.dongduk.daedongje.booth.domain.BoothCategory;
import com.dongduk.daedongje.booth.domain.BoothOperation;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

// 부스 목록 응답 한 칸. 명세서 3번 "부스 데이터 항목"과 같은 모양
public record BoothListResponse(
        Long id,
        BoothCategory category,
        String name,
        String organizer,
        Integer mapNumber,
        @JsonFormat(pattern = "HH:mm") LocalTime startTime, // "16:00:00"이 아니라 "16:00"으로 내보내기
        @JsonFormat(pattern = "HH:mm") LocalTime endTime,
        String locationName
) {

    // 운영 정보 엔티티 하나를 응답 객체 하나로 변환 (Booth + BoothOperation 정보를 합침)
    public static BoothListResponse from(BoothOperation operation) {
        Booth booth = operation.getBooth();
        return new BoothListResponse(
                booth.getId(),
                booth.getCategory(),
                booth.getName(),
                booth.getOrganizer(),
                operation.getMapNumber(),
                operation.getStartTime(),
                operation.getEndTime(),
                booth.getLocationName()
        );
    }
}