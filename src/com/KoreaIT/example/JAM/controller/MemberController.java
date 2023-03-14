package com.KoreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;
import com.KoreaIT.example.JAM.service.MemberService;

public class MemberController {
    private Connection conn;
    private Scanner sc;
    private MemberService memberService;
    
    public MemberController(Connection conn, Scanner sc) {
    	this.conn = conn;
    	this.sc = sc;
    	this.memberService = new MemberService();
    }
	public void doJoin() {
		String loginId = null;
		String loginPw = null;
		String loginPwChk = null;
		String name = null;
		System.out.println("== 회원 가입  ==");
        
		while( true ) {
			System.out.printf("로그인 아이디: ");
			loginId = sc.nextLine().trim();
			if(loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}
		
			boolean loginChk = memberService.loginChk(loginId);
			
			if( loginChk ) {
				System.out.printf("%s은(는) 이미 사용중인 아이디입니다\n", loginId);
				continue;
			}
			
			while( true ) {
				System.out.printf("로그인 비밀번호: ");
				loginPw = sc.nextLine().trim();
				
				if(loginPw.length() == 0) {
					System.out.println("비밀번호를 입력해주세요");
					continue;
				}
				boolean loginPwCheck = true;
				
				while( true ) {
					System.out.println("로그인 비밀번호 확인 : ");
					loginPwChk = sc.nextLine().trim();
					
					if( loginPwChk.length() == 0 ) {
						System.out.println("비밀번호 확인을 입력해주세요");
						continue;
					}
				
					if(loginPw.equals(loginPwChk) == false) {
						System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
						loginPwCheck = false;
					}
					break;
				}
				 if(loginPwCheck) {
				    break;
				 }
			  }
	          while( true ) {
				  System.out.println("이름: ");
				  name = sc.nextLine().trim();
				  
				  if(name.length() == 0) {
					  System.out.println("이름을 입력해주세요");
					  continue;
				  }
				  break;
			  }
		     
	   }
		
	}

}
