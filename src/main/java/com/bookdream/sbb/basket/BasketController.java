package com.bookdream.sbb.basket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookdream.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
	
	@GetMapping("")
	public String list() {
		return "basket/list";
	}
}