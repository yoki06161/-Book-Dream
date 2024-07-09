package com.bookdream.sbb.pay;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;import com.bookdream.sbb.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import com.bookdream.sbb.user.UserService;


@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class PayController {
	
	private final UserService uservice;
	
	@GetMapping("")
	public String order() {
		// 로그인했다면 주문 페이지로 이동
		String view = "";
		// 현재 인증된 사용자의 이메일 가져오기
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(email);
		
		// 로그인하지 않았다면 로그인화면으로 이동
		if(email == "anonymousUser") {
			view = "pay/loginform";
		} else {
			view = "pay/order";
		}
		
		return view;
	}
	
	@GetMapping("/")
	public String orderNotLogin() {
		return "pay/order";
	}
	
	@GetMapping("/login")
	public String loginform() {
		return "pay/loginform";
	}
	
	@GetMapping("/pay/success")
	public String paySuccess() {
		return "pay/pay_success";
	}
}
