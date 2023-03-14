package com.KoreaIT.example.JAM.service;

import java.sql.Connection;

import com.KoreaIT.example.JAM.dao.MemberDao;

public class MemberService{

	private MemberDao memberDao;
	private Connection conn;
	
	public MemberService() {
		this.memberDao = new MemberDao(conn);
	}
	public boolean loginChk(String loginId) {
		return memberDao.loginChk(loginId);
	}

}






