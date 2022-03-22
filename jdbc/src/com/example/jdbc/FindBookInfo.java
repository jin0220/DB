package com.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FindBookInfo {

	public static void main(String[] args) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Scanner sc = new Scanner(System.in);

		System.out.print("ã���� �ϴ� ������ �̸��� �Է� �ϼ��� >> ");
		String inputName = sc.nextLine();

		if (!inputName.equals(null) || !"".equals(inputName)) {
			try {
				// 1. JDBC ����̹� (Oracle) �ε�
				Class.forName("oracle.jdbc.driver.OracleDriver");

				// 2. Connection ������
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				conn = DriverManager.getConnection(url, "webdb", "1234");

				// 3. SQL�� �غ� / ���ε� / ����
				String query = " SELECT title, pubs, to_char(pub_date,'YYYY/MM/DD') pub_date, author_name \r\n"
						+ " FROM book b, author a \r\n" + " WHERE b.AUTHOR_ID = a.AUTHOR_ID \r\n"
						+ " AND   a.AUTHOR_NAME LIKE ? ";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + inputName + "%");
				rs = pstmt.executeQuery();

				// 4.���ó��
				while (rs.next()) {
					// int bookId = rs.getInt("book_id");
					String title = rs.getString("title");
					String pubs = rs.getString("pubs");
					String pub_date = rs.getString("pub_date");
					String authorName = rs.getString("author_name");
					System.out.println(authorName + ":" + title + "/" + pubs + "(" + pub_date + ")");
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
