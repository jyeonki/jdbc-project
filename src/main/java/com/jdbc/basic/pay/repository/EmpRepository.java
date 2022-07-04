package com.jdbc.basic.pay.repository;

import com.jdbc.basic.pay.domain.Emp;

import java.util.Map;

public interface EmpRepository {

    // 사원 정보 저장
    boolean save(Emp emp);

    // 사원 정보 삭제
    boolean remove(String empNo);

    // 사원 정보 수정 (직급)
    boolean modify(Emp emp);

    // 전체 사원 정보 조회
    Map<String, Emp> findAll();

    // 개별 사원 정보 조회
    Emp findOne(String empNo);
}
