package com.dongduk.daedongje.booth.repository;

import com.dongduk.daedongje.booth.domain.Booth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoothRepository extends JpaRepository<Booth, Long> {
    // JpaRepository가 제공하는 findById()로 부스 ID에 해당하는 부스를 조회
}