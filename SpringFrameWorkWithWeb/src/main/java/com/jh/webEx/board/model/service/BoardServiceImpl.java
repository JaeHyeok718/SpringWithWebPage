package com.jh.webEx.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.webEx.board.model.dao.BoardDao;
import com.jh.webEx.board.model.vo.Board;
import com.jh.webEx.common.model.vo.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int selectListCount() {
		
		return boardDao.selectListCount(sqlSession);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		
		return boardDao.selectList(sqlSession,pi);
	}
	
	//조회수 증가
	@Override
	public int increaseCount(int bno) {

		return boardDao.increaseCount(sqlSession,bno);
	}

	//상세조회
	@Override
	public Board boardDetail(int bno) {
		
		return boardDao.boardDetail(sqlSession,bno);
	}
	
	//게시글 등록
	@Override
	public int insertBoard(Board b) {

		return boardDao.insertBoard(sqlSession,b);
	}
	
	//게시글 수정
	@Override
	public int updateBoard(Board b) {
		
		return boardDao.updateBoard(sqlSession,b);
	}

	//게시글 삭제 
	@Override
	public int deleteBoard(int bno) {
		
		return boardDao.deleteBoard(sqlSession,bno);
	}

	

	
	//게시글 top 5
	@Override
	public ArrayList<Board> selectTopList() {
		
		return boardDao.selectTopList(sqlSession);
	}
	

	
	
	
	
	
	
	
	
	
	
	
}
