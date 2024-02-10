package com.jh.webEx.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jh.webEx.member.model.vo.Member;



@Repository //저장소 : 주로 DB(저장소)와 관련된 작업을 처리하는 곳
public class MemberDao {
	
	//로그인
	public Member loginMember(SqlSessionTemplate sqlSession,Member m) {
		return sqlSession.selectOne("memberMapper.loginMember", m);
	}
	
	//회원가입
	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertMember", m);
	}
	
	//아이디 중복체크
	public int checkId(SqlSessionTemplate sqlSession, String checkId) {
		
		return sqlSession.selectOne("memberMapper.checkId",checkId);
	}
	
	//회원정보수정
	public int updateMember(SqlSessionTemplate sqlSession,Member m) {
				
		return sqlSession.update("memberMapper.updateMember", m);
	}
	//회원삭제	
	public int deleteMember(SqlSessionTemplate sqlSession,String userId) {
			
		return sqlSession.delete("memberMapper.deleteMember",userId);
	}
	
	
}
