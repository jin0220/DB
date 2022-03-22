package com.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FindPostCode {

	public static void main(String[] args) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Scanner sc = new Scanner(System.in);

		System.out.print("건물명과 동을 입력하세요 >> ");
		String inputAddr = sc.nextLine();

		if (!inputAddr.equals(null) || !"".equals(inputAddr)) {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName("oracle.jdbc.driver.OracleDriver");

				// 2. Connection 얻어오기
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				conn = DriverManager.getConnection(url, "webdb", "1234");
				
				String building = inputAddr.split(" ")[0];
				String dong = inputAddr.split(" ")[1];

				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "SELECT * "
						+ " FROM post_sejong \r\n" + " WHERE building LIKE ? \r\n"
						+ " AND dong LIKE ? ";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + building + "%");
				pstmt.setString(2, "%" + dong + "%");
				rs = pstmt.executeQuery();

				// 4.결과처리
				while (rs.next()) {
					String zipcode = rs.getString("ZIPCODE");
					String sido = rs.getString("sido");
					String doro = rs.getString("doro");
					String building_1 = rs.getString("building");
					String dong_1 = rs.getString("dong");
					System.out.println("우편번호 : " + zipcode);
					System.out.println(sido + " " + doro + " " + building_1 + "("+ dong_1 + ")");
				}
			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			} finally {
				// 5. 자원정리
				try {
					if (rs != null) {
						rs.close();
					}
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
		sc.close();

	}

}
