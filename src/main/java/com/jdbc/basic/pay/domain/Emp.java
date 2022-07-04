package com.jdbc.basic.pay.domain;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

// 데이터베이스 emp 테이블의 행데이터를 저장할 객체
public class Emp {

    private String empNo; // 사원번호
    private String empName; // 사원명
    private int rankId; // 직급 ID

}
