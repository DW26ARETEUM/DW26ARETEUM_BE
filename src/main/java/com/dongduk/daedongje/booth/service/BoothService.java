package com.dongduk.daedongje.booth.service;

import com.dongduk.daedongje.booth.domain.BoothCategory;
import com.dongduk.daedongje.booth.domain.BoothOperation;
import com.dongduk.daedongje.booth.dto.BoothListResponse;
import com.dongduk.daedongje.booth.repository.BoothOperationRepository;
import com.dongduk.daedongje.global.exception.InvalidRequestException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 스프링이 관리하는 서비스 객체로 등록
@RequiredArgsConstructor // final 필드를 받는 생성자 자동 생성 → 스프링이 Repository를 넣어 줌
@Transactional(readOnly = true) // 조회만 하므로 읽기 전용
public class BoothService {

    private static final int KEYWORD_MAX_LENGTH = 50;
    private static final int IDS_MAX_SIZE = 100;

    private final BoothOperationRepository boothOperationRepository;

    // 부스 목록 조회: 날짜로 가져온 뒤 분류·검색어·찜 ID로 거르고 정렬해서 반환
    public List<BoothListResponse> getBooths(LocalDate date, BoothCategory category,
                                             String keyword, List<Long> ids) {
        String normalizedKeyword = normalizeKeyword(keyword);
        Set<Long> idSet = toIdSet(ids);

        return boothOperationRepository.findAllByDateWithBooth(date).stream()
                .filter(op -> category == null || op.getBooth().getCategory() == category)
                .filter(op -> normalizedKeyword == null
                        || containsIgnoreCase(op.getBooth().getName(), normalizedKeyword))
                .filter(op -> idSet == null || idSet.contains(op.getBooth().getId()))
                .sorted(Comparator.comparing((BoothOperation op) -> op.getBooth().getCategory())
                        .thenComparing(BoothOperation::getMapNumber))
                .map(BoothListResponse::from)
                .toList();
    }

    // 검색어 정리: 없거나 공백뿐이면 null(검색 안 함), 앞뒤 공백 제거, 50자 초과면 400
    private String normalizeKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        String trimmed = keyword.trim();
        if (trimmed.length() > KEYWORD_MAX_LENGTH) {
            throw new InvalidRequestException("검색어는 50자 이하로 입력해 주세요.");
        }
        return trimmed;
    }

    // 찜 ID 목록 검사 후 Set으로 변환: 없으면 null(필터 안 함), 100개 초과면 400
    private Set<Long> toIdSet(List<Long> ids) {
        if (ids == null) {
            return null;
        }
        if (ids.size() > IDS_MAX_SIZE) {
            throw new InvalidRequestException("부스 ID는 최대 100개까지 조회할 수 있습니다.");
        }
        return new HashSet<>(ids);
    }

    // 대소문자 무시하고 부분 일치하는지 확인 ("motif" 검색 → "MOTIF" 찾기)
    private boolean containsIgnoreCase(String target, String keyword) {
        return target.toLowerCase().contains(keyword.toLowerCase());
    }
}