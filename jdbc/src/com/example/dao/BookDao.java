package com.example.dao;

import java.util.List;

public interface BookDao {
	public List<BookVo> select() ;

	  public List<BookVo> getList();
	  public BookVo get(String id);
	  public boolean insert(BookVo authorVo);
	  public boolean delete(Long id);
	  public boolean update(BookVo authorVo);
	  
	  public List<BookVo> getList(String text);
}
