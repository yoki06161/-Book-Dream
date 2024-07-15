package com.bookdream.sbb.prod;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor.mode_return;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.bookdream.sbb.prod_repo.*;


// 스프링 실행시 로그인창 안뜨게한다
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// 기본적으로 prod를 쓰게 만듬. 만약 이거 지우면 밑에 링크들 다 앞에 /prod붙여줘야한다.
@RequestMapping("/prod")
// final필드 자동 생성용
@RequiredArgsConstructor
@Controller
public class Prod_Controller {

	// @Autowired 이미 생성된 빈을 가져온단 뜻
	@Autowired
	private final Prod_Service prodService;
//	private final review_repository re_repo;
	
	// 리스트
	// prod로 들어오는 주소 여기로
	@GetMapping("")
	// 자바에서 html로 데이터 전달할때 쓰는게 model
	public String prod_list(Model model) throws IOException {
		// 키밸류라 생각하면 된다. 여기서 설정한 Prod_Books가 html에서 불리는용, book_list는 여기의 값(데이터 지우신듯
		
//		List<Prod_Books> book_list = Prod_Crawling.getc_Datas();
//		// 크롤링된 데이터를 데이터베이스에 저장합니다.
//      prodService.saveBooks(book_list);

      // 데이터베이스에서 저장된 데이터를 가져와서 모델에 추가합니다.
      model.addAttribute("C_Books", prodService.getAllBooks());
      // 크롤링된 데이터 그대로 출력 
//		model.addAttribute("C_Books", book_list);
//		System.out.println("모델값");
//		System.out.println(model);
		return "prod/prod_list";
	}
	
	// 제품 상세보기.
	// @PathVariable은 url에 있는 변수 인식하는거.
	@GetMapping("/detail/{book_id}")
	public String prod_book(Model model, @PathVariable("book_id") Integer book_id) throws IOException{
		Prod_Books book = prodService.getProdBooks(book_id);
		model.addAttribute("book", book);
		
		// 리뷰 보여주기
		List<Prod_d_Review> p_list = this.prodService.getReview_List(book_id);
		model.addAttribute("p_list", p_list);
		
		return "prod/prod_detail";
	}
	
	// 리뷰 쓰기
	@PostMapping("/detail/write_review/{r_id}")
	public String write_review(Model model,@PathVariable("r_id") Integer id, @RequestParam("w_content") String content) {
		this.prodService.Write_Review(content, id);
		return String.format("redirect:/prod/detail/%s", id);
	}
	
	// 리뷰 답글 쓰기
	@PostMapping("/detail/write_answer/{a_id}")
	public String write_answer(Model model, @PathVariable("a_id") Integer id, @RequestParam("a_content") String content) {
		// 답글 가져오기
		Prod_d_Review review = this.prodService.getAnswer(id);
		
		// 답글 쓰기.()안의 값이 서비스의 ()랑 값이 같아야한다?
		this.prodService.Write_Answer(review, id, content);
		return String.format("redirect:/prod/detail/%s", id);
	}
	
	// db리스트 테스트 !!!!!!!!!!!내꺼
	// 그냥 테스트 리스트로 보내는거.
//	@GetMapping("/list_test")
//	public String list_t(Model model) {
//		List<Prod_d_Review> p_list = this.prodService.get_t_list();
//		model.addAttribute("p_list", p_list);
//		return "prod/list_test";
//	}
	
	// 테스트 리스트에서 상세보기로
	// 질문 상세보기
//	@GetMapping("/test_detail/{t_id}")
//	public String t_detail(Model model, @PathVariable("t_id") Integer id) throws DataNotFound {
//		Prod_d_Review pr = this.prodService.get_t_detail(id);
//		model.addAttribute("pr", pr);
//		return "prod/test_detail";
//	}
	
	// 답변하기
	@PostMapping("/test_detail/answer/c/{tt_id}")
	// 리퀘스트 파람은 name값이랑 똑같이.
	public String t_answer(Model model, @PathVariable("tt_id") Integer id, @RequestParam("t_content") String t_con) throws DataNotFound {
		Prod_d_Review pr = this.prodService.get_t_detail(id);
		this.prodService.create(pr, t_con);
		// 앞에 /prod를 안해서 이상하게 됐었다...
		return String.format("redirect:/prod/test_detail/%s", id);
	}
	
	
	
		
	
}