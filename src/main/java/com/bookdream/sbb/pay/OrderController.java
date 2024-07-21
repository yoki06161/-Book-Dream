package com.bookdream.sbb.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
	@Autowired
	private final PayService payService;
	private final OrdersService ordersService;

	@GetMapping("")
	public String order() {
		//		// 현재 인증된 사용자의 이메일 가져오기
		//		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		//		System.out.println(email);
		//		
		//		// 로그인하지 않았다면 로그인화면으로 이동
		//		if(email == "anonymousUser") {
		//			view = "pay/loginform";
		//		} else {
		//			// 로그인했다면 주문 페이지로 이동
		//			view = "pay/order";
		//		}	
		return "pay/order";
	}

//	@GetMapping("/success")
//	public String paySuccess() {
//		return "pay/order_success";
//	}
	@GetMapping("/success")
	public String paySuccess() {
		return "pay/order_success";
	}
}
