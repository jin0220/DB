package com.example.jdbc;

import java.sql.*;

public class AuthorInsertTest {

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
      String sql = "INSERT INTO author VALUES (seq_author_id.nextval, ?, ? )";
      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, "ȫ�浿"); // ù ��° ?�� ���� ��
      pstmt.setString(2, "ȫ�浿��"); // �� ��° ?�� ���� ��
      
      int count = pstmt.executeUpdate(); //insert, update, delete
      
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
