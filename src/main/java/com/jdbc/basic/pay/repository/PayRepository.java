package com.jdbc.basic.pay.repository;

import com.jdbc.basic.pay.domain.Emp;
import com.jdbc.basic.pay.domain.Pay;

import java.util.Map;

public interface PayRepository {

    Pay selectOneWholePay(String empNo);
    Map<String, Pay> selectAllWholePay();
}
