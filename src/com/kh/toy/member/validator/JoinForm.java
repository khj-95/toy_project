package com.kh.toy.member.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.kh.toy.member.model.service.MemberService;

public class JoinForm {
	
	private String userId;
	private String password;
	private String tell;
	private String email;
	HttpServletRequest request;
	private MemberService memberService = new MemberService();
	private Map<String,String> faildValidation = new HashMap<String,String>();
	
	public JoinForm(HttpServletRequest request) {
		this.request = request;
		this.userId = request.getParameter("userId");
		this.password = request.getParameter("password");
		this.email = request.getParameter("email");
		this.tell = request.getParameter("tell");
	}
	
	public boolean test() {
		
		boolean isFailed = false;
		
		// 사용자 아이디가 DB에 이미 존재하는 지 확인
		if(userId.contentEquals("") || memberService.selectMemberById(userId) != null) {
			faildValidation.put("userId",userId);
			isFailed = true;
		}
		
		// 비밀번호가 영어, 숫자, 특수문자 조합의 8자리 이상의 문자열인지 확인
		if(!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9])(?=.{8,})", password)) {
			faildValidation.put("password", password);
			isFailed = true;
		}
		
		// 전화번호가 숫자로만 이루어져 있는 지 확인
		if(!Pattern.matches("\\d{9,11}", tell)) {
			faildValidation.put("tell", tell);
			isFailed = true;
		} 
		
		if(isFailed) {
			request.getSession().setAttribute("joinValid", faildValidation);
			request.getSession().setAttribute("joinForm", this);
			return false;
		} else {
			request.getSession().removeAttribute("joinValid");
			request.getSession().removeAttribute("joinForm");
			return true;
		}
	}
	
	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getTell() {
		return tell;
	}

	public String getEmail() {
		return email;
	}

	

}
