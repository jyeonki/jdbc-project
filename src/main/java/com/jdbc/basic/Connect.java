package com.jdbc.basic;

import java.sql.Connection;
import java.sql.DriverManager;

// Oracle DB 연결
public class Connect {

    // 데이터 베이스 연결을 위한 정보 저장
    private final static String ACCOUNT = "sqld";  // 계정명
    private final static String PASSWORD = "1234"; // 비밀번호
    // 데이터 베이스의 위치정보 (DB URL) - DB회사마다 패턴이 다름
    private final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    // 데이터 베이스 접속에 쓸 드라이버 클래스 - DB회사마다 다름
    // oracle jdbc driver class name (google 검색)
    private final static String DRIVER = "oracle.jdbc.driver.OracleDriver";

    // DB 연결 메서드
    public static Connection makeConnection() {

        try {
            // 1. 드라이버 클래스를 동적 로딩
            Class.forName(DRIVER);

            // 2. 연결정보를 담아 연결 객체를 생성
            Connection conn = DriverManager.getConnection(URL, ACCOUNT, PASSWORD);
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
