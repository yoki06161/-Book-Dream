package com.bookdream.sbb.pay;

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
	public String order(HttpSession session) {
		// 로그인했다면 주문 페이지로 이동
		String view = "pay/order";
		String email = (String) session.getAttribute("userEmail");
		
		// 로그인하지 않았다면 로그인화면으로 이동
		if(email == null) {
			view = "pay/loginform";
		}
		
		return view;
	}
	
	@GetMapping("/login")
	public String loginform() {
		return "pay/loginform";
	}
}
