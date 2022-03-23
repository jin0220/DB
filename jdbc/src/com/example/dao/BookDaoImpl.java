package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dburl, "webdb","1234");
		}
		catch (ClassNotFoundException e) {
			System.err.println("JDBC 드라이버 로드 실패!");
		}
		return conn;
	}
	
	@Override
	public List<BookVo> select() {
		// TODO Auto-generated method stub
		return getList();
	}

	// 전체가져오기
	@Override
	public List<BookVo> getList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<BookVo> list = new ArrayList<>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT book_id, title, pubs, pub_date, author_id "
					+ "FROM book "
					+ "ORDER BY book_id ";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				BookVo bookVo = new BookVo(
						rs.getInt(1),
			            rs.getString(2),
			            rs.getString(3),
			            rs.getString(4),
			            rs.getString(5)						
						);
				list.add(bookVo);
			}
		} catch (SQLException e) {
			System.err.println("ERROR:" + e.getMessage());
		}
		
		return list;
	}

	@Override
	public BookVo get(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BookVo bookVo = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "SELECT book_id, title, pubs, pub_date, author_id "
					+ "FROM book "
					+ "WHERE book_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bookVo = new BookVo(
						rs.getInt(1),
			            rs.getString(2),
			            rs.getString(3),
			            rs.getString(4),
			            rs.getString(5)	
						);
			}
			
		}catch(SQLException e) {
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
		
		return bookVo;
	}

	@Override
	public boolean insert(BookVo bookVo) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    int insertedCount = 0;
	    
	    try {
	      conn = getConnection();
	      String sql = "INSERT INTO book " +
	          "VALUES(SEQ_BOOK_ID.NEXTVAL, " +
	          "?, ?, ?, ?)";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, bookVo.getTitle());
	      pstmt.setString(2, bookVo.getPubs());
	      pstmt.setString(3, bookVo.getPub_date());
	      pstmt.setInt(4, bookVo.getAuthor_id());
	      
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
	      String sql = "DELETE FROM book WHERE book_id=?";
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
	public boolean update(BookVo bookVo) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    int updatedCount = 0;
	    
	    try {
	      conn = getConnection();
	      String sql = "UPDATE book SET " +
	          "title=?, pubs=?, pub_date=?, author_id=? " +
	          "WHERE book_id=?";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, bookVo.getTitle());
	      pstmt.setString(2, bookVo.getPubs());
	      pstmt.setString(3, bookVo.getPub_date());
	      pstmt.setInt(4, bookVo.getAuthor_id());
	      pstmt.setInt(5, bookVo.getBook_id());
	      
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

}
