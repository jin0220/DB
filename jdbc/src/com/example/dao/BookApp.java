package com.example.dao;

import java.util.List;
import java.util.Scanner;

public class BookApp {

	public static void main(String[] args) {
		BookDao dao = new BookDaoImpl();
		
//		Long id = 21L;
//		
//		// 조회
//		List<BookVo> list = dao.select();
//		
//		for(BookVo vo : list) {
//			System.out.println(vo);
//		}
//		
//		// 삽입
//		BookVo bookVo_insert = new BookVo(0,"테스트", "테스트", "2022-03-23",1);
//		if(dao.insert(bookVo_insert)) {
//			System.out.println("입력 완료");
//		}
//
//		// 업데이트
//		BookVo bookVo_update = new BookVo(21, "test", "test", "2022-03-23",1);
//		if(dao.update(bookVo_update)) {
//			System.out.println("수정 완료");
//		}
//
//		// 삭제
//		if(dao.delete(id)) {
//			System.out.println("삭제 완료");
//		}
		
//		for(BookVo vo : dao.select()) {
//			System.out.println(vo);
//		}
		
		// 책 검색하기
		System.out.print("찾고 싶은 책의 제목/출판사/저자명을 입력하세요 >> ");
	    
	    Scanner sc = new Scanner(System.in);
	    String name = sc.nextLine();
	    
	    List<BookVo> bookList = dao.getList(name);
	 // for each 문으로 모든 데이터 출력
	    for(BookVo vo : bookList) {
	      System.out.println(vo);
	    }

	    sc.close();
	}

}
