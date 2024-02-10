package com.jh.webEx.member.model.service;

import com.jh.webEx.member.model.vo.Member;

public interface MemberService {
	
	//로그인 메소드
	
	Member loginMember(Member m);
	
	//회원가입 메소드
	int insertMember(Member m);

	//아이디 중복체크 메소드
	int checkId(String checkId);
	
	//회원정보 수정 메소드
	int updateMember(Member m);
		
	//회원탈퇴 메소드
	int deleteMember(String userId);
	
}
