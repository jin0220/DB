package com.example.dao;

import java.util.List;
import java.util.Scanner;

public class BookApp {

	public static void main(String[] args) {
		BookDao dao = new BookDaoImpl();
		
//		Long id = 21L;
//		
//		// ��ȸ
//		List<BookVo> list = dao.select();
//		
//		for(BookVo vo : list) {
//			System.out.println(vo);
//		}
//		
//		// ����
//		BookVo bookVo_insert = new BookVo(0,"�׽�Ʈ", "�׽�Ʈ", "2022-03-23",1);
//		if(dao.insert(bookVo_insert)) {
//			System.out.println("�Է� �Ϸ�");
//		}
//
//		// ������Ʈ
//		BookVo bookVo_update = new BookVo(21, "test", "test", "2022-03-23",1);
//		if(dao.update(bookVo_update)) {
//			System.out.println("���� �Ϸ�");
//		}
//
//		// ����
//		if(dao.delete(id)) {
//			System.out.println("���� �Ϸ�");
//		}
		
//		for(BookVo vo : dao.select()) {
//			System.out.println(vo);
//		}
		
		// å �˻��ϱ�
		System.out.print("ã�� ���� å�� ����/���ǻ�/���ڸ��� �Է��ϼ��� >> ");
	    
	    Scanner sc = new Scanner(System.in);
	    String name = sc.nextLine();
	    
	    List<BookVo> bookList = dao.getList(name);
	 // for each ������ ��� ������ ���
	    for(BookVo vo : bookList) {
	      System.out.println(vo);
	    }

	    sc.close();
	}

}
