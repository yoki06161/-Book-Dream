package com.bookdream.sbb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// 다른 사람이 만든 Book 클래스와 BookRepository를 사용하여 새로운 컨트롤러 구성
@Controller
@RequestMapping("/main") // 이 클래스의 모든 메소드의 기본 URL 경로를 '/main'으로 설정
public class MainController {

    @Autowired
//    private BookRepository bookRepository;

    // 메인 페이지로 이동, 책 목록을 보여줌
    @GetMapping("/")
    public String mainPage() {
   
        return "main"; // main.html Thymeleaf 템플릿을 반환
    }

    // 이벤트 페이지로 이동
    @GetMapping("/event")
    public String eventPage(Model model) {
        // 이벤트 관련 정보를 모델에 추가할 수 있음
        return "event"; // event.html Thymeleaf 템플릿을 반환
    }
}
