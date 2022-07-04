package com.jdbc.basic.pay.domain;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

// 데이터베이스 rank 테이블의 행데이터를 저장할 객체
public class Rank {

    private int rankId; // 직급 ID
    private String rankName; // 직급명
    private int basePay; // 기본급
    private double taxRate; // 세율
}
