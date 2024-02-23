package com.jh.webEx.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jh.webEx.board.model.vo.Board;
import com.jh.webEx.common.model.vo.PageInfo;



@Repository
public class BoardDao {

	public int selectListCount(SqlSessionTemplate sqlSession) {

		return sqlSession.selectOne("boardMapper.selectListCount");
	}

	public ArrayList<Board> selectList(SqlSessionTemplate sqlSession,PageInfo pi){
		
		//몇개씩 보여줄지
		int limit = pi.getBoardLimit();
		//몇개를 건너뛸지 
		int offset = (pi.getCurrentPage() -1 ) * limit;
		
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		//rowbounds 객체를 전달해야하는데 전달할 파라미터(전달값)이 없다면 null을 넣어서 메소드 호출하기.
		return (ArrayList)sqlSession.selectList("boardMapper.selectList",null,rowBounds);
		
	}
	//조회수 증가
	public int increaseCount(SqlSessionTemplate sqlSession,int bno) {
		
//		int result = sqlSession.update("boardMapper.increaseCount",bno);
//		return result;
		
		return sqlSession.update("boardMapper.increaseCount",bno);
	}

	//상세조회
	public Board boardDetail(SqlSessionTemplate sqlSession, int bno) {
		
		return sqlSession.selectOne("boardMapper.boardDetail", bno);
	}

	//게시글 등록
	public int insertBoard(SqlSessionTemplate sqlSession, Board b) {
		
		return sqlSession.insert("boardMapper.insertBoard", b);
	}
	
	//게시글 수정
	public int updateBoard(SqlSessionTemplate sqlSession, Board b) {

		return sqlSession.update("boardMapper.updateBoard",b);
	}

	
	//게시글 삭제
	public int deleteBoard(SqlSessionTemplate sqlSession, int bno) {
		
		return sqlSession.delete("boardMapper.deleteBoard",bno);
	}
	
	public ArrayList<Board> selectTopList(SqlSessionTemplate sqlSession) {
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectTopList");
	}
	
	

	
	
	
	
	
	
}
