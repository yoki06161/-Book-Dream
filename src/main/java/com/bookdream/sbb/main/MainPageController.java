package com.bookdream.sbb.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/mainpage") // 이 클래스의 모든 메소드의 기본 URL 경로를 '/mainpage'로 설정
public class MainPageController {
    @Autowired
    private MainRepository mainRepository;

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<Main> mains = mainRepository.findAll();
        model.addAttribute("mains", mains);
        model.addAttribute("main", new Main()); // 도서 추가 폼을 위한 빈 Main 객체를 모델에 추가
        return "main/main"; // templates.main 템플릿 이름
    }
}
