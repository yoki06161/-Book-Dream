package com.bookdream.sbb.pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	@PostMapping("/addProducts")
	public ResponseEntity<String> addProducts(@RequestBody List<Map<String, Object>> selectedItems) {
	    try {
	        List<Map<String, Object>> sessionData = new ArrayList<>();
	        for (Map<String, Object> data : selectedItems) {
	            Map<String, Object> sessionEntry = new HashMap<>();
	            sessionEntry.put("pay_id", data.get("pay_id"));
	            sessionEntry.put("book_id", data.get("book_id"));
	            sessionEntry.put("count", data.get("count"));
	            sessionEntry.put("count_price", data.get("count_price"));
	            sessionData.add(sessionEntry);    
	        }
	        System.out.println(sessionData);
	        
	        // 데이터 검증
	        if (sessionData.isEmpty()) {
	            return ResponseEntity.badRequest().body("No data to save");
	        }
	        
	        ordersService.saveOrdersItems(sessionData);
	        return ResponseEntity.ok("Products added successfully");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
	    }
	}

	@GetMapping("/success/{imp_uid}")
	public String paySuccess(Model model, @PathVariable("imp_uid") String pay_id) {
		System.out.println(pay_id);
		Optional<Pay> optionalPay = (Optional<Pay>) payService.getPaysById(pay_id);
		if (optionalPay.isPresent()) {
			model.addAttribute("pay", optionalPay.get());
		} else {
			// 예외 처리 또는 에러 메시지 처리
			model.addAttribute("error", "Payment not found");
		}
		return "pay/order_success";
	}
}
