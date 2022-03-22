package com.example.jdbc;

import java.sql.*;

public class BookSelect {

  public static void main(String[] args) {
    // 0. import java.sql.*;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      // 1. JDBC ����̹� (Oracle) �ε�
      Class.forName("oracle.jdbc.driver.OracleDriver");

      // 2. Connection ������
      String url = "jdbc:oracle:thin:@localhost:1521:xe";
      conn = DriverManager.getConnection(url, "webdb", "1234");
      
      // 3. SQL�� �غ� / ���ε� / ����
      String sql = " select * from book ";
      pstmt = conn.prepareStatement(sql);

      rs = pstmt.executeQuery();

      // 4.���ó��
      while (rs.next()) {
          int bookId = rs.getInt("book_id");
          String bookTitle = rs.getString("title");
          String bookPubs = rs.getString("pubs");
          String bookPubDate = rs.getString("pub_date");
          String author_id = rs.getString("author_id");
          System.out.println(bookId + "\t" + bookTitle + "\t" + bookPubs + "\t"
        		  + bookPubDate + "\t" + author_id + "\t");
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

}
