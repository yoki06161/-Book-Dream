package com.bookdream.sbb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bookdream.sbb.basket.BasketService;

@Controller
public class MainController {
	
	@Autowired
	private BasketService basketService;

	@GetMapping("/")	
	public String index(Model model, String id) {
//		Long count = basketService.getCountBasketsById(id);
//		
//		count = (count != null) ? count : 0L; // null 체크 및 0으로 치환
//		
//	    model.addAttribute("count", count);
		return "layout";
	}
}
