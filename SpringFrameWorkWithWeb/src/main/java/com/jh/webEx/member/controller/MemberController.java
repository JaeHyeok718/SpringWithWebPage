package com.jh.webEx.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.jh.webEx.member.model.service.MemberService;
import com.jh.webEx.member.model.vo.Member;



@Controller //Controller타입의 어노테이션을 붙여주면 빈 스캐닝을 통해서 자동 bean등록
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	

	
	//암호화 작업 후 로그인 메소드
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m
						   ,HttpSession session
						   ,ModelAndView mv) {

		/*
		 * 	ModelAndView 
		 * -model은 데이터를 key-value 세트로 담을 수 있는 공간이라면
		 * -view는 응답 뷰에 대한 정보를 담을 수있는 공간 
		 * Model과 View(응답뷰)가 합쳐진 형태 
		 * ModelAndView를 사용하여 응답하려면 반환형이 String이 아닌 ModelAndView여야 한다.
		 * 
		 * */
		
		//아이디를 가지고 데이터베이스에서 일치하는 회원정보 조회
		Member loginUser = memberService.loginMember(m);
		
		//조회해온 loginUser 정보에 있는 비밀번호(암호화되어있음) 와 
		//사용자가 입력해서 전달받은 비밀번호를 대조하기 
//		bcryptPasswordEncoder.matches(평문, 암호문) 를 이용 (일치하면 true 아니면 false)
		//사용자에게 입력받은 비밀번호 : m.getUserPwd() / 데이터베이스에서 조회해온 암호문은 : loginUser.getUserPwd() 
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) { //성공
			
			session.setAttribute("loginUser", loginUser);
			
			mv.setViewName("redirect:/"); 
			
		}else {//로그인 실패
			//에러메세지 담아서 에러페이지로 포워딩 시키기 
			
//			model.addAttribute("errorMsg","로그인 실패"); - model 객체 사용법
			mv.addObject("errorMsg","로그인 실패"); //- ModelAndView 객체 사용법
			
			mv.setViewName("common/errorPage");
		}	
		
		return mv;
	}
	
	
	
	//로그아웃 
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		
		//세션에 담겨있는 loginUser정보 지우기
		session.removeAttribute("loginUser");
		
		//로그아웃처리 후 메인페이지 재요청
		return "redirect:/";
	}
	
	//회원가입 페이지로 이동
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		
		//페이지 이동만 하면 되니 return 한줄처리 
		return "member/memberEnrollForm";
	}
	
	//회원등록
	@RequestMapping("insert.me")
	public String insertMember(Member m
							  ,Model model
							  ,HttpSession session) {		
		
		//암호화 작업 : bcryptPasswordEncoder.encode(평문);
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		
		
		m.setUserPwd(encPwd); //암호화된 비밀번호로 변경 
		
		// insertMember로 작업하기 성공시 메인페이지로 (성공메세지 alertMsg 담기)  
		// 실패시 에러페이지로 (model에 에러메세지 담기)  
		
		int result = memberService.insertMember(m);
		
		
		if(result>0) { //성공
			session.setAttribute("alertMsg", "회원가입 성공");
			
			return "redirect:/";//재요청
		}else { //실패
			model.addAttribute("errorMsg","회원가입 실패");
			return "common/errorPage";//포워딩
		}
		
	}
	
	@ResponseBody
	@RequestMapping("checkId.me")
	public String checkId(String checkId) {
		
		//사용자가 입력한 아이디가 데이터베이스에 존재하는지 중복체크하기 
		int count = memberService.checkId(checkId);
		
		if(count>0) { //사용하면 안된다(중복)
			return "NNNNN";
		}else { //사용 가능
			return "NNNNY";
		}
		
	}
	
	
}
