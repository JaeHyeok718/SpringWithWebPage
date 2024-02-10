package com.jh.webEx.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.webEx.member.model.dao.MemberDao;
import com.jh.webEx.member.model.vo.Member;



@Service //해당 클래스를 service의 역할로 사용하겠다.
public class MemberServiceImpl implements MemberService {

	
	//private MemberDao memberDao = new MemberDao();
	
	
	//스프링이 관리할수있도록 어노테이션 등록
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	//로그인
	@Override
	public Member loginMember(Member m) {

		Member loginUser = memberDao.loginMember(sqlSession,m);
		
		//@Autowired로 객체 생명주기 관리를 스프링 컨테이너가 하고 있기 때문에
		//close로 자원 반납할 필요가 없다. 컨테이너가 사용후 자동반납해줌
		
		return loginUser;
	}
	//회원등록
	@Override
	public int insertMember(Member m) {
		//commit rollback 해줄 필요 없음 sqlSessionTemplate이 알아서 처리한다. 
		return  memberDao.insertMember(sqlSession,m);
	}
	//아이디중복체크 
	@Override
	public int checkId(String checkId) {
		return memberDao.checkId(sqlSession,checkId);
	}

	//회원정보수정
	@Override
	public int updateMember(Member m) {
		return memberDao.updateMember(sqlSession,m);
	}
		
	//회원탈퇴 메소드
	@Override
	public int deleteMember(String userId) {
			return memberDao.deleteMember(sqlSession,userId);
	}
	
	
	
	
	
	
}
