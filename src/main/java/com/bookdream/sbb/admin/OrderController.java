//package com.bookdream.sbb.admin;
//
//import java.security.Principal;
//import java.util.Optional;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import com.bookdream.sbb.admin.OrderHistDto;  // 추가된 임포트
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor
//public class OrderController {
//
//	private final OrderService orderService;  // OrderService를 인스턴스 변수로 선언
//
//	@GetMapping(value = {"/orders", "/orders/{page}"})
//	public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
//		
//		Pageable pageable = PageRequest.of(page.orElse(0), 4);
//		Page<OrderListDto> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);
//		
//		model.addAttribute("orders", ordersHistDtoList);
//		model.addAttribute("page", pageable.getPageNumber());
//		model.addAttribute("maxPage", 5);
//		
//		return "order/orderHist";
//	}
//}
