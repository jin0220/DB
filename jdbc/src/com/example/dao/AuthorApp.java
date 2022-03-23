package com.example.dao;

import java.util.List;

public class AuthorApp {

  public static void main(String[] args) {
    // �������̽� ���۷��� ������ ����Ŭ������ ��ü �����
    AuthorDao dao = new AuthorDaoImpl();
    
    // 1. ������� ��ü�� select() ȣ��
    List<AuthorVo> list = dao.select();
    
    // for each ������ ��� ������ ���
    for(AuthorVo vo : list) {
      System.out.println(vo);
    }
    
    // 2. ������� ��ü�� insert() ȣ��
    AuthorVo authorVo_insert = new AuthorVo(1,"test","test"); //�� �Է��ϱ�
    if(dao.insert(authorVo_insert)) {
    	System.out.println("�Է� �Ϸ�");
    }

    // 3. ������� ��ü�� update() ȣ��
    AuthorVo authorVo_update = new AuthorVo(12,"�׽�Ʈ","�׽�Ʈ"); //�� �Է��ϱ�
    if(dao.update(authorVo_update)) {
    	System.out.println("���� �Ϸ�");
    }

    // 4. ������� ��ü�� delete() ȣ��
    Long id = 12L;
    if(dao.delete(id)) {
    	System.out.println("���� �Ϸ�");
    }
    
  }

}
