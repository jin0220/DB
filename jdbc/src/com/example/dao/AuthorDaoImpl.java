package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AuthorDaoImpl implements AuthorDao{
  
  private Connection getConnection() throws SQLException {
    Connection conn = null;
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
      conn = DriverManager.getConnection(dburl, "webdb", "1234");
    } catch (ClassNotFoundException e) {
      System.err.println("JDBC 드라이버 로드 실패!");
    }
    return conn;
  }
  
  @Override
  public List<AuthorVo> getList() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    //  데이터 전송을 위한 리스트
    List<AuthorVo> list = new ArrayList<>();
    
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      String sql = " SELECT author_id, author_name, author_desc " 
                 + " FROM author "
                 + " order by author_id ";
      
      rs = stmt.executeQuery(sql);
      
      while(rs.next()) {
        AuthorVo authorVO = new AuthorVo(
            rs.getInt(1),
            rs.getString(2),
            rs.getString(3)
            );
        list.add(authorVO);
      }
    } catch (SQLException e) {
      System.err.println("ERROR:" + e.getMessage());
    }
    
    return list;
  }

  @Override
  public AuthorVo get(String id) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    AuthorVo authorVo = null;
    ResultSet rs = null;
    
    try {
      conn = getConnection();
      String sql = " SELECT author_id, " +
                   " author_name, author_desc " +
                   " FROM author " +
                   " WHERE author_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, Integer.parseInt(id)); // 바인딩
      
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        authorVo = new AuthorVo(
            rs.getInt(1),
            rs.getString(2),
            rs.getString(3)
            );
      }
    } catch (SQLException e) {
      System.err.println("ERROR:" + e.getMessage());
    } finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (Exception e) {
        System.err.println("ERROR:" + e.getMessage());
      }
    }
    return authorVo;
  }

  @Override
  public boolean insert(AuthorVo authorVo) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    int insertedCount = 0;
    
    try {
      conn = getConnection();
      String sql = "INSERT INTO author " +
          "VALUES(SEQ_AUTHOR_ID.NEXTVAL, " +
          "?, ?)";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, authorVo.getAuthor_name());
      pstmt.setString(2, authorVo.getAuthor_desc());
      
      insertedCount = pstmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("ERROR:" + e.getMessage());
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (Exception e) {
        System.err.println("ERROR:" + e.getMessage());
      }
    }
    return insertedCount == 1;
  }

  @Override
  public boolean delete(Long id) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    int deletedCount = 0;
    
    try {
      conn = getConnection();
      String sql = "DELETE FROM author WHERE author_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setLong(1, id);
      
      deletedCount = pstmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("ERROR:" + e.getMessage());
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (Exception e) {
        System.err.println("ERROR:" + e.getMessage());
      }
    }
    return deletedCount == 1;
  }

  @Override
  public boolean update(AuthorVo authorVo) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    int updatedCount = 0;
    
    try {
      conn = getConnection();
      String sql = "UPDATE AUTHOR SET " +
          "author_name=?, author_desc=? " +
          "WHERE author_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, authorVo.getAuthor_name());
      pstmt.setString(2, authorVo.getAuthor_desc());
      pstmt.setInt(3, authorVo.getAuthor_id());
      
      updatedCount = pstmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("ERROR:" + e.getMessage());
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (Exception e) {
        System.err.println("ERROR:" + e.getMessage());
      }
    }
    return 1 == updatedCount;
  }

  @Override
  public List<BookVo> getList(String text) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    //  데이터 전송을 위한 리스트
    List<BookVo> list = new ArrayList<>();
    
    try {
      conn = getConnection();
      
      String sql = 
          " SELECT b.book_id, \r\n" + 
          "        b.title, \r\n" + 
          "        b.pubs, \r\n" + 
          "        to_char( b.pub_date,'YYYY-MM-DD'), \r\n" + 
          "        a.author_name\r\n" + 
          " FROM author a, book b\r\n" + 
          " where a.author_id = b.author_id\r\n" + 
          " and (b.title || b.pubs || a.author_name) LIKE '%'|| ? ||'%' ";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, text); // 바인딩
      rs = pstmt.executeQuery();
      
      while(rs.next()) {
        BookVo vo = new BookVo(
            rs.getInt(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            rs.getString(5)
            );
        list.add(vo);
      }
    } catch (SQLException e) {
      System.err.println("ERROR:" + e.getMessage());
    }
    
    return list;
  }

  @Override
  public List<AuthorVo> select() {
	    return getList();
  }
}
