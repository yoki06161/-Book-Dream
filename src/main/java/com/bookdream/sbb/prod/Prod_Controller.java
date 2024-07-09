package com.bookdream.sbb.prod;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor.mode_return;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


// 스프링 실행시 로그인창 안뜨게한다
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// final필드 자동 생성용
@RequiredArgsConstructor
@Controller
public class Prod_Controller {

//	private final Prod_Crawling crawling;
	
	// 리스트
	// prod로 들어오는 주소 여기로
	@GetMapping("/prod")
	// 컨트롤러에서 뷰로 데이터 전달할때 쓰는게 model
	public String prod_list(Model model) throws IOException {
		List<Prod_Books> book_list = Prod_Crawling.getc_Datas();
		// 키밸류라 생각하면 된다. 여기서 설정한 Prod_Books가 html에서 불리는용, book_list는 여기의 값
		model.addAttribute("C_Books", book_list);
		
//		System.out.println("모델값");
//		System.out.println(model);
		return "prod/prod_list";
	}
	
	// 제품 상세보기
	@GetMapping("/prod/detail")
	public String prod_book(Model model,@RequestParam("l_title") String title, @RequestParam("l_img") String img, @RequestParam("l_price") String price, HttpSession session) throws IOException {
		// 여기서 Model model = null;로 하면 모델값 널이라고 오류뜬다. 위의 ()안에서 설정해야함.
		String book_details = Prod_Crawling6.getc_Datas();
		model.addAttribute("C_detail", book_details);
//		System.out.println("모델출력이 안된다고?");
//		System.out.println("테스트용 모델값" + model);
		
//		System.out.println("상세페이지에 출력될 타이틀 " + title);
//		System.out.println("상세페이지에 출력될 이미지 " + img);
//		System.out.println("상세페이지에 출력될 가격 " + price);
		session.setAttribute("s_title", title);
		session.setAttribute("s_img", img);
		session.setAttribute("s_price", price);
		return "prod/prod_detail";
	}
	
	// 제품 상세보기
		@GetMapping("/session")
		public String session_t() {
//			System.out.println("상세페이지에 출력될 타이틀 " + title);
//			System.out.println("상세페이지에 출력될 이미지 " + img);
//			session.setAttribute("s_title", title);
//			session.setAttribute("s_img", img);
			return "prod/session_test";
		}
	
}
