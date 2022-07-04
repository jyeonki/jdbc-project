package com.jdbc.basic.pay.repository;

import com.jdbc.basic.Connect;
import com.jdbc.basic.pay.domain.Emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

// Oracle DBMS에 사원 정보를 CRUD하는 클래스
public class EmpRepositoryImpl implements EmpRepository {

    @Override
    public boolean save(Emp emp) {

        String sql = "INSERT INTO emp VALUES (TO_CHAR(sysdate,'yymmdd')|| + LPAD(seq_emp.nextval, 2, '0'), ?,?)";

        try (Connection conn = Connect.makeConnection()) {
            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기!!

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, emp.getEmpName());
            pstmt.setInt(2, emp.getRankId());

            int result = pstmt.executeUpdate();

            if (result != 0)
            { // 결과가 0이 아니면 commit
                conn.commit();
                return true;
            } else
            {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(String empNo) {

        String sql = "DELETE FROM emp WHERE emp_no = ?";

        try (Connection conn = Connect.makeConnection()) {

            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기!!

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, empNo);

            int result = pstmt.executeUpdate();

            if (result != 0)
            { // 결과가 0이 아니면 commit
                conn.commit();
                return true;
            } else
            {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modify(Emp emp) {

        String sql = "UPDATE emp SET rank_id = ? WHERE emp_no = ?";

        try (Connection conn = Connect.makeConnection()) {
            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기!!

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, emp.getRankId());
            pstmt.setString(2, emp.getEmpNo());

            int result = pstmt.executeUpdate();

            if (result != 0)
            { // 결과가 0이 아니면 commit
                conn.commit();
                return true;
            } else
            {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Emp> findAll() {

        Map<String, Emp> empMap = new HashMap<>();

        String sql = "SELECT * FROM emp ORDER BY emp_no";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                Emp emp = new Emp(
                        rs.getString("emp_no")
                        , rs.getString("emp_nm")
                        , rs.getInt("rank_id")
                );
                empMap.put(emp.getEmpNo(), emp);
            }
            return empMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Emp findOne(String empNo) {

        String sql = "SELECT * FROM emp WHERE emp_no = ?";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, empNo);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                Emp emp  = new Emp(
                        rs.getString("emp_no")
                        , rs.getString("emp_nm")
                        , rs.getInt("rank_id")
                );

                return emp;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
