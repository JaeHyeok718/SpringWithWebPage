package com.jh.webEx.board.model.service;

import java.util.ArrayList;

import com.jh.webEx.board.model.dao.BoardDao;
import com.jh.webEx.board.model.vo.Board;



public interface BoardService {
	//게시글 개수
	int selectListCount();
	//게시글 목록
	ArrayList<Board> selectList(com.jh.webEx.common.model.vo.PageInfo pi);
	
	//조회수증가
	int increaseCount(int bno);
	
	//상세조회
	Board boardDetail(int bno);
	
	//게시글 등록
	int insertBoard(Board b);
	
	//게시글 수정
	int updateBoard(Board b);
	
	//게시글 삭제
	int deleteBoard(int bno);

	//게시글 top 5 조회
	ArrayList<Board> selectTopList();
	
	
	
	
	
	
	
	
	
	
	
	
	
}
