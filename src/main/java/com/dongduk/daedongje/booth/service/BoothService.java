package com.dongduk.daedongje.booth.service;

import com.dongduk.daedongje.booth.domain.Booth;
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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoothService {

    private static final int KEYWORD_MAX_LENGTH = 50;
    private static final int IDS_MAX_SIZE = 100;

    private final BoothOperationRepository boothOperationRepository;

    // 부스 목록 조회: ids가 있으면 즐겨찾기(날짜 무관), 없으면 선택 날짜 기준으로 가져온 뒤 분류·검색어로 거르고 정렬
    public List<BoothListResponse> getBooths(LocalDate date, BoothCategory category,
                                             String keyword, List<Long> ids) {
        String normalizedKeyword = normalizeKeyword(keyword);
        Set<Long> idSet = toIdSet(ids);

        return findOperations(date, idSet).stream()
                .filter(op -> category == null || op.getBooth().getCategory() == category)
                .filter(op -> normalizedKeyword == null || matchesKeyword(op.getBooth(), normalizedKeyword))
                .sorted(Comparator.comparing((BoothOperation op) -> op.getBooth().getCategory())
                        .thenComparing(BoothOperation::getMapNumber))
                .map(BoothListResponse::from)
                .toList();
    }

    // 조회 대상 고르기: 찜 ID가 없으면 그 날짜 전체, 있으면 찜한 부스를 날짜 상관없이 부스당 하나씩
    private List<BoothOperation> findOperations(LocalDate date, Set<Long> idSet) {
        if (idSet == null) {
            return boothOperationRepository.findAllByDateWithBooth(date);
        }
        if (idSet.isEmpty()) {
            return List.of();
        }
        return boothOperationRepository.findAllByBoothIdsWithBooth(idSet).stream()
                .collect(Collectors.toMap(
                        op -> op.getBooth().getId(),     // 부스 ID가 같으면 한 칸으로 합침
                        op -> op,
                        (a, b) -> pickPreferred(a, b, date)))
                .values().stream()
                .toList();
    }

    // 양일 운영 부스 중 보여줄 하루 고르기: 선택한 날짜 우선, 둘 다 아니면 더 이른 날짜
    private BoothOperation pickPreferred(BoothOperation a, BoothOperation b, LocalDate date) {
        if (a.getOperationDate().equals(date)) return a;
        if (b.getOperationDate().equals(date)) return b;
        return a.getOperationDate().isBefore(b.getOperationDate()) ? a : b;
    }

    // 검색어가 부스명 또는 운영 주체(동아리 등)에 포함되는지 확인 (운영 주체는 null일 수 있음)
    private boolean matchesKeyword(Booth booth, String keyword) {
        return containsIgnoreCase(booth.getName(), keyword)
                || (booth.getOrganizer() != null && containsIgnoreCase(booth.getOrganizer(), keyword));
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