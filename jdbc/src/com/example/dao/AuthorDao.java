package com.example.dao;

import java.util.List;

public interface AuthorDao {
  public List<AuthorVo> select() ;

  public List<AuthorVo> getList();
  public AuthorVo get(String id);
  public boolean insert(AuthorVo authorVo);
  public boolean delete(Long id);
  public boolean update(AuthorVo authorVo);
  
  public List<BookVo> getList(String text);
}
