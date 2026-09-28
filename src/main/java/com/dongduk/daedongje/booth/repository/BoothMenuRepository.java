package com.dongduk.daedongje.booth.repository;

import com.dongduk.daedongje.booth.domain.BoothMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoothMenuRepository
        extends JpaRepository<BoothMenu, Long> {

    // 해당 부스의 메뉴를 표시 순서 → 메뉴 ID 순으로 조회
    List<BoothMenu> findAllByBooth_IdOrderBySortOrderAscIdAsc(
            Long boothId
    );
}