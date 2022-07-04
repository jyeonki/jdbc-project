package com.jdbc.basic.pay.controller;

import com.jdbc.basic.pay.domain.Emp;
import com.jdbc.basic.pay.domain.Pay;
import com.jdbc.basic.pay.domain.Rank;
import com.jdbc.basic.pay.repository.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 데이터 저장, 관리 클래스
public class PayController {

    // 급여 정보가 저장될 Map ( key: 사원번호, value: 급여정보 )
    private Map<String, Emp> empMap;
    private Map<Integer, Rank> rankMap;
    private Map<String, Pay> payMap;


    private final EmpRepository empRepository;
    private final RankRepository rankRepository;
    private final PayRepository payRepository;

    public PayController() {
        this.empRepository = new EmpRepositoryImpl();
        this.rankRepository = new RankRepositoryImpl();
        this.payRepository = new PayRepositoryImpl();
    }

    // 사원 정보 입력 기능
    public void insertEmp(Emp emp) {

        empMap = new HashMap<>();

        // 1. 메모리에 정보 저장하기
        empMap.put(emp.getEmpNo(), emp);

        // 2. DB에 저장 명령
        empRepository.save(emp);
    }

    // 직급 정보 입력 기능
    public void insertRank(Rank rank) {

        rankMap = new HashMap<>();

        // 1. 메모리에 정보 저장하기
        rankMap.put(rank.getRankId(), rank);

        // 2. DB에 저장 명령
        rankRepository.save(rank);
    }

    // 특정 사원의 정보 조회 기능
    public Emp findOneEmp(String empNo) {

        return empRepository.findOne(empNo);
    }

    // 특정 직급 정보 조회 기능
    public Rank findOneRank(int rankId) {

        return rankRepository.findOneRank(rankId);
    }

    // 특정 직급 정보 조회 기능
    public int findOneRankId(String rankName) {

        return rankRepository.findOneId(rankName);
    }

    // 사원 직급 수정 기능
    public boolean updateEmp(String empNo, int rankId) {

        // 1. DB에서 사원정보 불러오기
        Emp target = findOneEmp(empNo);

        if (target != null)
        {
            // 2. 사원 직급 수정
            target.setRankId(rankId);

            // 3. DB에 수정 반영
            empRepository.modify(target);
            return true;

        } else
        {
            return false;
        }
    }

    // 직급 수정 기능
    public boolean updateRank(Rank rank) {

        // 1. DB에서 직급정보 불러오기
        Rank target = findOneRank(rank.getRankId());

        if (target != null)
        {
            // 2. 직급 정보 수정
            target.setRankName(rank.getRankName());
            target.setBasePay(rank.getBasePay());
            target.setTaxRate(rank.getTaxRate());

            // 3. DB에 수정 반영
            rankRepository.modify(target);
            return true;

        } else
        {
            return false;
        }
    }

    // 특정 사원 삭제 기능
    public boolean deleteEmp(String empNo) {

        return empRepository.remove(empNo);
    }

    // 특정 직급 삭제 기능
    public boolean deleteRank(int rankId) {

        return rankRepository.remove(rankId);
    }

    // 전체 사원 정보 조회 기능
    public List<Emp> findAllEmp() {

        Map<String , Emp> employees = empRepository.findAll();
        empMap = employees;

        List<Emp> empList = new ArrayList<>();
        for (String empNo : empMap.keySet()) {
            empList.add(empMap.get(empNo));
        }

        return empList;
    }

    // 전체 직급 정보 조회 기능
    public List<Rank> findAllRank() {

        Map<Integer, Rank> ranks = rankRepository.findAll();
        rankMap = ranks;

        List<Rank> rankList = new ArrayList<>();
        for (int rankId : rankMap.keySet()) {
            rankList.add(rankMap.get(rankId));
        }

        return rankList;
    }


    // 사원번호로 조회했을 때 사원 존재 유무를 리턴해주는 메서드
    public boolean hasEmp(String empNo) {

        return empRepository.findOne(empNo) != null;
    }

    // 직급번호로 조회했을 때 직급 존재 유무를 리턴해주는 메서드
    public boolean hasRank(int rankId) {

        return rankRepository.findOneRank(rankId) != null;

    }

    // 특정 사원의 전체 정보 조회 기능
    public Pay selectOneWholePay(String empNo) {

        return payRepository.selectOneWholePay(empNo);
    }

    // 전체 사원의 전체 정보 조회 기능
    public List<Pay> selectAllWholePay() {

        Map<String , Pay> pay = payRepository.selectAllWholePay();
        payMap = pay;

        List<Pay> payList = new ArrayList<>();
        for (String empNo : payMap.keySet()) {
            payList.add(payMap.get(empNo));
        }

        return payList;
    }
}












