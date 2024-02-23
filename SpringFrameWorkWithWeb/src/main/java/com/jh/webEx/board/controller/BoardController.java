package com.jh.webEx.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jh.webEx.board.model.service.BoardService;
import com.jh.webEx.board.model.vo.Board;
import com.jh.webEx.common.model.vo.PageInfo;
import com.jh.webEx.common.template.Pagination;
import com.jh.webEx.member.model.vo.Member;


@Controller
public class BoardController {
	
	
	@Autowired
	private BoardService boardService;
	

	
	@RequestMapping("list.bo")
	public String selectBoardList(@RequestParam(value="currentPage",defaultValue="1")
							      int currentPage
							     ,Model model) {
		
		//페이징처리된 게시글 조회하기 
		//현재 페이지정보 (currentPage) - 처음 게시판을 들어올때는 페이지정보가 없으니 defualtValue에 설정해주기 
		
		//전체 게시글 개수 (listCount) - selectListCount() 메소드명 
		int listCount = boardService.selectListCount();
		
		int boardLimit = 5;
		//한페이지에서 보여줘야하는 게시글 개수 (boardLimit): 5
		int pageLimit = 10;
		//페이징바 개수 (pageLimit) : 10 
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		ArrayList<Board> list = boardService.selectList(pi);
		
		
		//페이징 처리된 게시글목록 조회해서 listview에 보여주기
		
		model.addAttribute("list",list);
		model.addAttribute("pi",pi);
		
		
		return "board/boardListView";
	}
	
	@GetMapping("detail.bo")
	public String boardDetail(int bno
							 ,Model model) {
		
		
		//조회수증가(update - DML)
		int result = boardService.increaseCount(bno); 
		
		//조회수 증가가 이루어지면 해당 페이지를 조회한다. 
		if(result>0) { //성공
			Board b = boardService.boardDetail(bno);
			
			
			model.addAttribute("b",b);
			
		}else {
			
			model.addAttribute("errorMsg","게시글 조회 실패");
			return "common/errorPage";
			
		}
		
		
		return "board/boardDetailView";
	}
	
	
	@GetMapping("insert.bo")
	public String boardEnrollForm() {
		//작성하기 페이지로 포워딩처리 
		return "board/boardEnrollForm";
	}
	
	@PostMapping("insert.bo")
	public String insertBoard(Board b
						   ,MultipartFile upfile
						   ,HttpSession session) {
		
		//전달받은 파일의 이름이 있는지 확인 (넘어온 파일이 없으면 getOriginalFilename()이 빈문자열 반환)
		if(!upfile.getOriginalFilename().equals("")) {
		
			String changeName = saveFile(upfile,session);
			
			//8.데이터 베이스에 등록할 변경이름,원본이름 Board VO에 담기
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/"+changeName);
			
			
		}
		
		//if문 벗어나서 작업하기 
		//데이터베이스에 정보 등록하기.
		int result = boardService.insertBoard(b);
		
		if(result>0) {
			session.setAttribute("alertMsg", "게시글 등록 성공");
			return "redirect:list.bo";
		}else {
			session.setAttribute("alertMsg", "게시글 등록 실패");
			return "common/errorPage";
		}
	}
	
	
	@GetMapping("update.bo")
	public String updateForm(int bno
							 ,Model model) {
		
		//게시글 번호로 해당 정보 조회해오기
		Board b = boardService.boardDetail(bno);
		model.addAttribute("b", b);
		
		return "board/boardUpdateForm"; 
	}
	
	@PostMapping("update.bo")
	public String updateBoard(Board b
							,MultipartFile reUpFile
							,HttpSession session
							,Member m) {
		//게시글에 이미 첨부파일이 있는 경우 or 없는 경우
		//파일이 담겨 넘어왔다면(새로운 첨부파일이 있는 경우)
		if(!reUpFile.getOriginalFilename().equals("")) {
			//새로운 첨부파일이 있고 기존 첨부파일이 있는 경우 기존 첨부파일 삭제 
			//데이터베이스에 기존 첨부파일 정보에 새로운 첨부파일 정보 덮어쓰기 
			//새로운 첨부파일 서버에 업로드 작업 
			String changeName = saveFile(reUpFile,session);
			
			//기존에 파일이 있다면 
			if(!b.getOriginName().equals("")) {
				//new File 객체로 해당 경로에 있는 파일(업로드되어있던)을 delete메소드로 지우기
				
				File f = new File(session.getServletContext().getRealPath(b.getChangeName()));
				f.delete();
				
			}
			
			//Board 객체에 originName과 changeName을 담기 (데이터베이스 수정용)
			b.setOriginName(reUpFile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/"+changeName);
		}
		
		//전달된 파일이 있다면 세팅이 되었을테니 해당 정보 포함하여 데이터베이스에 전달하기 
		//update - DML 
		int result = boardService.updateBoard(b);
		
		if(result>0) { //수정 성공 
			session.setAttribute("alertMsg", "게시글 수정 성공");
			return "redirect:detail.bo?bno="+b.getBoardNo();
		}else {//수정 실패 
			session.setAttribute("alertMsg", "게시글 수정 실패");
			return "common/errorPage";
		}
		
		
		//이미 있는데 새로 들어온 파일이 있는경우 
		//기존 파일을 지우고 새로 들어온 파일을 등록하기 
		//기존 파일 경로를 File 객체로 잡고 생성하여 delete메소드로 해당위치 파일 제거하기
		//수정하기 작업 후 수정완료 메세지와 함께 해당 상세보기 페이지로 이동 
		//실패시 에러메세지와 함께 에러페이지로 이동
		
	}
	
	@PostMapping("delete.bo")
	public String deleteBoard(int bno
						   ,String filePath
						   ,HttpSession session) {
		
		//삭제처리 
		int result = boardService.deleteBoard(bno);
		
		
		if(result>0) {
			//파일 삭제처리 
			if(!filePath.equals("")) {//넘어온 파일데이터가 있을때 
				File f = new File(session.getServletContext().getRealPath(filePath));
				f.delete();
			}
			session.setAttribute("alertMsg", "게시글 삭제 성공");
		}else {
			session.setAttribute("alertMsg", "게시글 삭제 실패");
		}
		
		return "redirect:list.bo";

		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//파일명 수정 모듈
	public String saveFile(MultipartFile upfile
						  ,HttpSession session) {
		//파일명 수정하기 
		//1. 원본파일명 추출 
		String originName = upfile.getOriginalFilename(); 
		
		//2. 시간 추출하기 (년월일시분초) - util.Date 
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//3. 뒤에 붙일 랜덤값 5자리 추출하기 
		int ranNum = (int)(Math.random()*90000+10000);
		
		//4.원본파일명에서 확장자 추출하기 test.txt - 뒤에서부터 . 을찾고 그 뒤로 잘라내기 .txt 
		String ext = originName.substring(originName.lastIndexOf("."));
		
		//5. 2,3,4 이어붙여서 변경이름 만들기(업로드이름)
		String changeName = currentTime+ranNum+ext;
		
		//6.저장시킬 실질적인 물리적 경로 추출하기 
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		
		//7. 경로와 수정파일명으로 파일 업로드 하기(경로+파일명) 파일객체를 생성한뒤 해당 파일 객체를 업로드시킨다.
		try {
			upfile.transferTo(new File(savePath+changeName));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return changeName;
	}
	
	
	//게시글 top5 조회
	

	@ResponseBody
	@RequestMapping("topList.bo")
	public ArrayList<Board> selectTopList() {
		
	ArrayList<Board> list = boardService.selectTopList();
	
	
		return list;
		
	
		
	}

	
	
	//AOP를 적용시키는 사례 (위에 작성되어있는 topList를 이용해보기)
	

	
	
	
	
	
	
	
	
	
}
