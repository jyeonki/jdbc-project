package com.jdbc.basic.pay.repository;

import com.jdbc.basic.Connect;
import com.jdbc.basic.pay.domain.Emp;
import com.jdbc.basic.pay.domain.Pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

// Oracle DBMS에 사원 정보를 CRUD하는 클래스
public class PayRepositoryImpl implements PayRepository {

    @Override
    public Pay selectOneWholePay(String empNo) {

        String sql = "SELECT A.EMP_NO\n" +
                "     , A.EMP_NM\n" +
                "     , A.RANK_ID\n" +
                "     , B.RANK_NM\n" +
                "     , B.BASE_PAY\n" +
                "     , B.TAX_RATE\n" +
                "     , B.BASE_PAY * (1 - B.TAX_RATE) AS NET_SALARY\n" +
                "  FROM emp A\n" +
                "  JOIN rank_info B   ON a.rank_id = b.rank_id\n" +
                " WHERE 1 = 1\n" +
                "   AND EMP_NO = ?";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, empNo);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                Pay pay = new Pay(
                        rs.getString("emp_no")
                        , rs.getString("emp_nm")
                        , rs.getInt("rank_id")
                        , rs.getString("rank_nm")
                        , rs.getInt("base_pay")
                        , rs.getDouble("tax_rate")
                        , rs.getInt("net_salary")
                );
                return pay;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Pay> selectAllWholePay() {

        Map<String, Pay> payMap = new HashMap<>();

        String sql = "SELECT A.EMP_NO\n" +
                "     , A.EMP_NM\n" +
                "     , A.RANK_ID\n" +
                "     , B.RANK_NM\n" +
                "     , B.BASE_PAY\n" +
                "     , B.TAX_RATE\n" +
                "     , B.BASE_PAY * (1 - B.TAX_RATE) AS NET_SALARY\n" +
                "  FROM emp A\n" +
                "  JOIN rank_info B   ON a.rank_id = b.rank_id\n" +
                "   ORDER BY a.emp_no";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            Pay pay = null;
            while (rs.next()) {
                pay = new Pay(
                        rs.getString("emp_no")
                        , rs.getString("emp_nm")
                        , rs.getInt("rank_id")
                        , rs.getString("rank_nm")
                        , rs.getInt("base_pay")
                        , rs.getDouble("tax_rate")
                        , rs.getInt("net_salary")
                );
                payMap.put(pay.getEmpNo(), pay);
            }
            return payMap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
