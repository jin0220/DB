package com.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookInfo {

	public static void main(String[] args) {
		// 0. import java.sql.*;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. JDBC 드라이버 (Oracle) 로딩
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 2. Connection 얻어오기
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            conn = DriverManager.getConnection(url, "webdb", "1234");
            System.out.println("접속성공");

            // 3. SQL문 준비 / 바인딩 / 실행
            String query = " SELECT * " + 
                           " FROM book b, author a " + 
                           " WHERE b.AUTHOR_ID = a.AUTHOR_ID ";
            pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();

            // 4.결과처리
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String pubs = rs.getString("pubs");
                String pub_date = rs.getString("pub_date");
                String authorName = rs.getString("author_name");
                System.out.println(bookId + "\t" + title + "\t" + pubs + "\t"
                		+ pub_date + "\t"+ authorName);
            }

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
