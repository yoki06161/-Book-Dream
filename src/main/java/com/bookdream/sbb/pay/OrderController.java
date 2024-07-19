package com.bookdream.sbb.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	@Autowired
	private final PayService payService;
	private final OrdersService ordersService;

	@GetMapping("/order")
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

	@GetMapping("/order/success")
	public String paySuccess() {
		return "pay/order_success";
	}

	@PostMapping("/order/success")
	public String paySuccess(
			@RequestParam("name") String name, 
			@RequestParam("phone") String phone, 
			@RequestParam("pw") String pw,
			@RequestParam("address") String address, 
			@RequestParam("detail_address") String detail_address, 
			@RequestParam("post_code") String post_code, 
			@RequestParam("options") String request, 
			@RequestParam("totalSum") String total_price) {
		// 처리 로직
		System.out.println("Name: " + name);
		System.out.println("Phone: " + phone);
		System.out.println("Password: " + pw);
		System.out.println("Address: " + address);
		System.out.println("Detail Address: " + detail_address);
		System.out.println("Postcode: " + post_code);
		System.out.println("Options: " + request);
		System.out.println("Total Sum: " + total_price);

		// 결제 테이블에 값 저장
		payService.savePays(name,phone,pw,address,detail_address,post_code,request,total_price);
		// 주문 테이블에 값 저장(for문)
		//ordersService.saveOrder();

		// 결제 성공 페이지 리턴
		return "pay/order_success";
	}
}
