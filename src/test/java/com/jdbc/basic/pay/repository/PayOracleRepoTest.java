package com.jdbc.basic.pay.repository;

import com.jdbc.basic.pay.domain.Emp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PayOracleRepoTest {

    PayRepository repository = new PayOracleRepo();

    @Test
    @DisplayName("사원별 급여정보를 DB에 삽입해야 한다.")
    void insertTest() {

        Emp park = new Emp();
        park.setEmpName("박대박");
        park.setEmpRank("대리");
        park.calc(park.getEmpRank());
        park.calcNetSalary();

        System.out.println(park);
        boolean result = repository.save(park);
        assertTrue(result);
    }

    @Test
    @DisplayName("특정 사원의 정보를 삭제해야 한다")
    void removeTest() {

        // given
        String empNum = "22070115";

        // when - 테스트 상황
        boolean result = repository.remove(empNum);

        // then - 예상 결과
        Emp pay = repository.findOne(empNum);
        assertNull(pay); // 객체가 null인지 확인
    }

    @Test
    @DisplayName("특정 사원 정보를 조회해야 한다.")
    void findOneTest() {

        String empNum = "22070102";

        Emp payOne = repository.findOne(empNum);
        System.out.println(payOne);
        assertEquals("나대리", payOne.getEmpName());
    }

    @Test
    @DisplayName("사원의 직급이 수정되어야 한다.")
    void modifyTest() {

        // given
        String empNum = "22070105";

        Emp pay = repository.findOne(empNum);
        // findOne을 먼저 생성하고 modify를 실시

        System.out.println(pay);

        pay.setEmpRank("부장");
        pay.calc(pay.getEmpRank());
        pay.calcNetSalary();

        // when
        boolean result = repository.modify(pay);

        // then
        Emp newPay = repository.findOne(empNum);
        assertEquals("부장", pay.getEmpRank());
    }

    @Test
    @DisplayName("전직원의 급여정보가 조회되어야 한다.")
    void findAllTest() {

        Map<String, Emp> payMap = repository.findAll();

        for (String empNum : payMap.keySet()) {
            System.out.println(payMap.get(empNum));
        }
    }

    @Test
    @DisplayName("전직원 급여 평균이 구해져야 한다.")
    void getEmpPayAverageTest() {

        Map<String, Emp> payMap = repository.findAll();
        int total = 0;

        for (Emp pay : payMap.values()) {
            total += pay.getNetSalary();
        }
        double avg = total / payMap.size();

        System.out.println(total);
        System.out.println(avg);
    }
}