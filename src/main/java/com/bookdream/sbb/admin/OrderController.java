package com.bookdream.sbb.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookdream.sbb.user.UserCreateForm;
import com.bookdream.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
	private final AdminService userService;
	
	@GetMapping("/admin")
	public String sinup() {
		return "/admin";
	}
}
