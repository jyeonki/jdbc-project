package com.jdbc.basic.pay.repository;

import com.jdbc.basic.pay.domain.Rank;

import java.util.Map;

public interface RankRepository {

    // 직급 정보 저장
    boolean save(Rank rank);

    // 직급 정보 삭제
    boolean remove(int rankId);

    // 직급 정보 수정
    boolean modify(Rank rank);

    // 전체 직급 정보 조회
    Map<Integer, Rank> findAll();

    // 개별 직급 정보 조회
    Rank findOneRank(int rankId);
    int findOneId(String rankName);

}
