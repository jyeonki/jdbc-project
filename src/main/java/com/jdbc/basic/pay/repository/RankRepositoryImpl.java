package com.jdbc.basic.pay.repository;

import com.jdbc.basic.Connect;
import com.jdbc.basic.pay.domain.Emp;
import com.jdbc.basic.pay.domain.Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

// Oracle DBMS에 직급정보를 CRUD하는 클래스
public class RankRepositoryImpl implements RankRepository {

    @Override
    public boolean save(Rank rank) {

        String sql = "INSERT INTO rank_info VALUES (?,?,?,?)";

        try (Connection conn = Connect.makeConnection()) {
            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기!!

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, rank.getRankId());
            pstmt.setString(2, rank.getRankName());
            pstmt.setInt(3, rank.getBasePay());
            pstmt.setDouble(4, rank.getTaxRate());

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
    public boolean remove(int rankId) {

        String sql = "DELETE FROM rank_info WHERE rank_id = ?";

        try (Connection conn = Connect.makeConnection()) {

            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기!!

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, rankId);

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
    public boolean modify(Rank rank) {

        String sql = "UPDATE rank_info SET rank_nm = ?, base_pay = ?, tax_rate = ? WHERE rank_id = ?";

        try (Connection conn = Connect.makeConnection()) {
            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기!!

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, rank.getRankName());
            pstmt.setInt(2, rank.getBasePay());
            pstmt.setDouble(3, rank.getTaxRate());
            pstmt.setInt(4, rank.getRankId());

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
    public Map<Integer, Rank> findAll() {

        Map<Integer, Rank> rankMap = new HashMap<>();

        String sql = "SELECT * FROM rank_info ORDER BY rank_id";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Rank rank = new Rank(
                        rs.getInt("rank_id")
                        , rs.getString("rank_nm")
                        , rs.getInt("base_pay")
                        , rs.getDouble("tax_rate")
                );
                rankMap.put(rank.getRankId(), rank);
            }
            return rankMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Rank findOneRank(int rankId) {

        String sql = "SELECT * FROM rank_info WHERE rank_id = ?";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, rankId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                Rank rank  = new Rank(
                        rs.getInt("rank_id")
                        , rs.getString("rank_nm")
                        , rs.getInt("base_pay")
                        , rs.getDouble("tax_rate")
                );

                return rank;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public int findOneId(String rankName) {

        String sql = "SELECT rank_id FROM rank_info WHERE rank_nm = ?";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, rankName);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                return rs.getInt("rank_id");
            }
            return -1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
