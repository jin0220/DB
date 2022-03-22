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
			// 1. JDBC ����̹� (Oracle) �ε�
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection ������
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "1234");
			System.out.println("���Ӽ���");

			// 3. SQL�� �غ� / ���ε� / ����
			String sql = "INSERT INTO book VALUES (seq_book_id.nextval, ?, ?, TO_DATE(?, 'yyyy-mm-dd'), ? )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "�׽�Ʈ"); // ù ��° ?�� ���� ��
			pstmt.setString(2, "�׽�Ʈ"); // �� ��° ?�� ���� ��
			pstmt.setString(3, "2022-03-22");
			pstmt.setString(4, "1");
			
			int count = pstmt.executeUpdate(); // insert, update, delete

			// 4. ��� ó��			
			System.out.println(count + "�� ó��");

		} catch (ClassNotFoundException e) {
			System.out.println("error: ����̹� �ε� ���� - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. �ڿ�����
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
