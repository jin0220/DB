package com.example.dao;

import java.util.List;

public class AuthorApp {

  public static void main(String[] args) {
    // 인터페이스 레퍼런스 변수에 구현클래스의 객체 만들기
    AuthorDao dao = new AuthorDaoImpl();
    
    // 1. 만들어진 객체의 select() 호출
    List<AuthorVo> list = dao.select();
    
    // for each 문으로 모든 데이터 출력
    for(AuthorVo vo : list) {
      System.out.println(vo);
    }
    
    // 2. 만들어진 객체의 insert() 호출
    AuthorVo authorVo_insert = new AuthorVo(1,"test","test"); //값 입력하기
    if(dao.insert(authorVo_insert)) {
    	System.out.println("입력 완료");
    }

    // 3. 만들어진 객체의 update() 호출
    AuthorVo authorVo_update = new AuthorVo(12,"테스트","테스트"); //값 입력하기
    if(dao.update(authorVo_update)) {
    	System.out.println("수정 완료");
    }

    // 4. 만들어진 객체의 delete() 호출
    Long id = 12L;
    if(dao.delete(id)) {
    	System.out.println("삭제 완료");
    }
    
  }

}
