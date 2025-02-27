package test;


import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;

class DBPerformanceTest {

    @Test
    void testDbPerformance() {
        String url = "jdbc:mysql://localhost:3306/test_db";
        String user = "admin";
        String password = "0000";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM patient WHERE p_user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "USER5000000");

                long startTime = System.nanoTime();
                ResultSet rs = stmt.executeQuery();
                long endTime = System.nanoTime();

                if (rs.next()) {
                    System.out.println("환자 검색 성공: " + rs.getString("P_Name"));
                }
                double elapsedTime = (endTime - startTime) / 1_000_000.0; // 밀리초 변환
                System.out.println("DB 검색 속도: " + elapsedTime + "ms");

                // 속도 테스트 성공 여부 검증
                assertTrue(elapsedTime < 500, "검색 시간이 500ms를 초과함!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}