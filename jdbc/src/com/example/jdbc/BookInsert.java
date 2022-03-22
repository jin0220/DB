package com.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookInsert {

	public static void main(String[] args) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "1234");
			System.out.println("접속성공");

			// 3. SQL문 준비 / 바인딩 / 실행
			String sql = "INSERT INTO book VALUES (seq_book_id.nextval, ?, ?, TO_DATE(?, 'yyyy-mm-dd'), ? )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "테스트"); // 첫 번째 ?에 들어가는 값
			pstmt.setString(2, "테스트"); // 두 번째 ?에 들어가는 값
			pstmt.setString(3, "2022-03-22");
			pstmt.setString(4, "1");
			
			int count = pstmt.executeUpdate(); // insert, update, delete

			// 4. 결과 처리			
			System.out.println(count + "건 처리");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

	}

}
