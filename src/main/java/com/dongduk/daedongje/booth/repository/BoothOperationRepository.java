package com.dongduk.daedongje.booth.repository;

import com.dongduk.daedongje.booth.domain.BoothOperation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoothOperationRepository extends JpaRepository<BoothOperation, Long> {

    // 특정 날짜의 운영 정보를 부스 정보까지 한 번에 조회 (join fetch로 쿼리 1번)
    @Query("select o from BoothOperation o join fetch o.booth where o.operationDate = :date")
    List<BoothOperation> findAllByDateWithBooth(@Param("date") LocalDate date);
}