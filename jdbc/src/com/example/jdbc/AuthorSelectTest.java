package com.example.jdbc;

import java.sql.*;

public class AuthorSelectTest {

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
      String sql = " select * from author ";
      pstmt = conn.prepareStatement(sql);

      rs = pstmt.executeQuery();

      // 4.���ó��
      while (rs.next()) {
          int authorId = rs.getInt("author_id");
          String authorName = rs.getString("author_name");
          String authorDesc = rs.getString("author_desc");
          System.out.println(authorId + "\t" + authorName + "\t" + authorDesc + "\t");
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
