package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.KjmDemoApplication;
import com.example.demo.interceptor.BeforeActionInterceptor;
import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class UsrMemberController {

    private final BeforeActionInterceptor beforeActionInterceptor;

    private final BoardService boardService;
	
	@Autowired
	private Rq rq;

	@Autowired
	private MemberService memberService;

    UsrMemberController(BoardService boardService, BeforeActionInterceptor beforeActionInterceptor) {
        this.boardService = boardService;
        this.beforeActionInterceptor = beforeActionInterceptor;
    }

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogin(HttpServletRequest req) {

		Rq rq = (Rq) req.getAttribute("rq");

		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 성공", "/");
	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpServletRequest req) {
		return "/usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.isEmptyOrNull(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해");
		}
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는(은) 없는 아이디야", loginId));
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", "비밀번호가 일치하지 않습니다");
		}

		rq.login(member);

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), "/");
	}

	@RequestMapping("/usr/member/join")
	public String showJoin(HttpServletRequest req){
		
		return "usr/member/join";
	}
	
		
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(HttpServletRequest req, String loginId, String loginPw, String name,
			String nickname, String cellphoneNum, String email) {

		if (Ut.isEmptyOrNull(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디 입력해");
		}
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-1", "비밀번호 입력해");

		}
		if (Ut.isEmptyOrNull(name)) {
			return Ut.jsHistoryBack("F-1", "이름 입력해");

		}
		if (Ut.isEmptyOrNull(nickname)) {
			return Ut.jsHistoryBack("F-1", "닉네임 입력해");

		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return Ut.jsHistoryBack("F-1", "전화번호 입력해");

		}
		if (Ut.isEmptyOrNull(email)) {
			return Ut.jsHistoryBack("F-1", "이메일 입력해");

		}

		ResultData doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (doJoinRd.isFail()) {
			return Ut.jsHistoryBack(doJoinRd.getResultCode(), doJoinRd.getMsg());
		}

		Member member = memberService.getMemberById((int) doJoinRd.getData1());
				return Ut.jsReplace("S-1", "회원가입에 성공했습니다", "../home/main");
	}
	
	
	@RequestMapping("/usr/member/doIncreaseCountRd")
	@ResponseBody
	public String doIncreaseCountRd(HttpServletRequest req, String doAction){
		
		Rq rq = (Rq) req.getAttribute("rq");
		
		System.out.println("========================================");
		System.out.println(rq.getIsLoginMemberId());
		
		
		
		return Ut.f("S-1","좋아요 성공");
	}
	

}