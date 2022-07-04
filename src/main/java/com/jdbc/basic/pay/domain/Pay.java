package com.jdbc.basic.pay.domain;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Pay {

    private String empNo; // 사원번호
    private String empName; // 사원명
    private int rankId; // 직급 ID
    private String rankName; // 직급명
    private int basePay; // 기본급
    private double taxRate; // 세율
    private int netSalary;  // 순급여
}
