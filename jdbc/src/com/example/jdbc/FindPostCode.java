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

		System.out.print("�ǹ���� ���� �Է��ϼ��� >> ");
		String inputAddr = sc.nextLine();

		if (!inputAddr.equals(null) || !"".equals(inputAddr)) {
			try {
				// 1. JDBC ����̹� (Oracle) �ε�
				Class.forName("oracle.jdbc.driver.OracleDriver");

				// 2. Connection ������
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				conn = DriverManager.getConnection(url, "webdb", "1234");
				
				String building = inputAddr.split(" ")[0];
				String dong = inputAddr.split(" ")[1];

				// 3. SQL�� �غ� / ���ε� / ����
				String query = "SELECT * "
						+ " FROM post_sejong \r\n" + " WHERE building LIKE ? \r\n"
						+ " AND dong LIKE ? ";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + building + "%");
				pstmt.setString(2, "%" + dong + "%");
				rs = pstmt.executeQuery();

				// 4.���ó��
				while (rs.next()) {
					String zipcode = rs.getString("ZIPCODE");
					String sido = rs.getString("sido");
					String doro = rs.getString("doro");
					String building_1 = rs.getString("building");
					String dong_1 = rs.getString("dong");
					System.out.println("�����ȣ : " + zipcode);
					System.out.println(sido + " " + doro + " " + building_1 + "("+ dong_1 + ")");
				}
			} catch (ClassNotFoundException e) {
				System.out.println("error: ����̹� �ε� ���� - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			} finally {
				// 5. �ڿ�����
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
