package com.bookdream.sbb.prod;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;


// 스프링 실행시 로그인창 안뜨게한다
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// final필드 자동 생성용
@RequiredArgsConstructor
@Controller
public class Prod_controller {

	private final Prod_crawling crawling;
	
	// 리스트
	// prod로 들어오는 주소 여기로
	@GetMapping("/prod")
	public String prod_list(Model model) throws IOException {
		List<Prod_Books> book_list = Prod_crawling.getc_Datas();
		model.addAttribute("Prod_Books", book_list);
		return "prod/prod_list";
	}
	
	// 제품 상세보기
	@GetMapping("/prod/book")
	public String prod_book() {
		
		return "prod/prod_book";
	}
	
	
}
