package com.dongduk.daedongje.booth.service;

import com.dongduk.daedongje.booth.domain.Booth;
import com.dongduk.daedongje.booth.dto.BoothDetailResponse;
import com.dongduk.daedongje.booth.dto.BoothMenuResponse;
import com.dongduk.daedongje.booth.dto.BoothOperationResponse;
import com.dongduk.daedongje.booth.exception.BoothNotFoundException;
import com.dongduk.daedongje.booth.repository.BoothMenuRepository;
import com.dongduk.daedongje.booth.repository.BoothOperationRepository;
import com.dongduk.daedongje.booth.repository.BoothRepository;
import com.dongduk.daedongje.global.exception.InvalidRequestException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoothDetailService {

    private final BoothRepository boothRepository;
    private final BoothOperationRepository boothOperationRepository;
    private final BoothMenuRepository boothMenuRepository;

    // 로컬·배포 환경에 맞는 이미지 서버 주소
    @Value("${app.image-base-url}")
    private String imageBaseUrl;

    public BoothDetailResponse getBoothDetail(Long boothId) {

        // 부스 ID는 1 이상의 정수여야 함
        if (boothId == null || boothId <= 0) {
            throw new InvalidRequestException(
                    "부스 ID는 1 이상의 정수여야 합니다."
            );
        }

        // 부스가 없으면 404 처리에 사용할 예외 발생
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(BoothNotFoundException::new);

        // 해당 부스의 전체 운영 일정을 응답 DTO로 변환
        List<BoothOperationResponse> operations =
                boothOperationRepository
                        .findAllByBooth_IdOrderByOperationDateAscStartTimeAscIdAsc(
                                boothId
                        )
                        .stream()
                        .map(operation -> new BoothOperationResponse(
                                operation.getOperationDate(),
                                operation.getStartTime(),
                                operation.getEndTime(),
                                operation.getMapNumber(),
                                toImageUrl(operation.getLocationImagePath())
                        ))
                        .toList();

        // 해당 부스의 메뉴를 표시 순서대로 조회하고 DTO로 변환
        List<BoothMenuResponse> menus =
                boothMenuRepository
                        .findAllByBooth_IdOrderBySortOrderAscIdAsc(boothId)
                        .stream()
                        .map(BoothMenuResponse::from)
                        .toList();

        // 기본정보·소개·아이콘·운영 일정·메뉴를 하나의 응답으로 조합
        return new BoothDetailResponse(
                booth.getId(),
                booth.getCategory(),
                booth.getName(),
                booth.getOrganizer(),
                booth.getLocationName(),
                booth.getDescription(),
                toImageUrl(booth.getIconImagePath()),
                operations,
                menus
        );
    }

    // DB의 이미지 경로에 서버 주소를 붙여 완전한 URL 생성
    private String toImageUrl(String imagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            return null;
        }

        // 서버 주소 끝의 /와 경로 시작의 /가 겹치지 않도록 처리
        String baseUrl = imageBaseUrl.replaceAll("/+$", "");
        String path = imagePath.startsWith("/")
                ? imagePath
                : "/" + imagePath;

        return baseUrl + path;
    }
}