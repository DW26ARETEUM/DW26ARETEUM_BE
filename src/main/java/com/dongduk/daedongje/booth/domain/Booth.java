package com.dongduk.daedongje.booth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity // 이 클래스가 DB 테이블(booth)과 연결된다는 표시
@Getter // 모든 필드의 getter 자동 생성 (getName() 등)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA가 쓰는 빈 생성자, 외부에서 함부로 못 쓰게 protected
public class Booth {

    @Id // 기본키(PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id를 DB가 자동으로 1, 2, 3... 증가
    private Long id;

    @Column(nullable = false, length = 50) // null 불가, 최대 50자
    private String name;

    @Column(length = 50) // nullable 생략 = null 허용 (솜컬렉션 등은 운영 주체 없음)
    private String organizer;

    @Enumerated(EnumType.STRING) // DB에 "PUB" 같은 이름으로 저장 (숫자 저장 X)
    @JdbcTypeCode(SqlTypes.VARCHAR) // ← 추가: DB 컬럼이 VARCHAR라고 알려 줌 (MySQL ENUM 타입으로 착각 방지)
    @Column(nullable = false, length = 20)
    private BoothCategory category;

    @Column(nullable = false, length = 50)
    private String locationName; // DB 컬럼명은 자동으로 location_name이 됨

    @Column(columnDefinition = "TEXT")
    private String description; // 부스 상세 소개. 긴 내용을 저장할 수 있도록 TEXT 사용, 없으면 null

    @Column(length = 255)
    private String iconImagePath; // 주점 아이콘 이미지 경로. DB 컬럼명: icon_image_path, 없으면 null
}