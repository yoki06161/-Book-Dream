package com.bookdream.sbb.freeboard;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/freeboard")
public class FreeboardController {

    @Autowired
    private FreeboardService freeboardService;

    private final String uploadDir = "C:/Users/TJ/git/Book-Dream/src/main/resources/static/image/freeboard/";

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("freeboards", freeboardService.findAll());
        return "freeboard/list"; // 템플릿 경로
    }

    @GetMapping("/{id}")
    public String getFreeboardById(@PathVariable Long id, Model model) {
        Freeboard freeboard = freeboardService.findById(id);
        model.addAttribute("freeboard", freeboard);
        return "freeboard/detail"; // 템플릿 경로
    }

    @GetMapping("/form")
    public String formFreeboard(Model model) {
        model.addAttribute("freeboard", new Freeboard());
        return "freeboard/form"; // 템플릿 경로
    }

    @PostMapping("/form")
    public String formFreeboard(@Valid @ModelAttribute Freeboard freeboard, BindingResult result,
                                @RequestParam("image") MultipartFile file, Model model) {
        if (result.hasErrors()) {
            return "freeboard/form"; // 템플릿 경로
        }

        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + ".png";
            String filePath = Paths.get(uploadDir, fileName).toString();
            File destFile = new File(filePath);
            try {
                file.transferTo(destFile);
                freeboard.setImage(fileName); // 파일 이름을 자유게시판에 설정
            } catch (Exception e) {
                model.addAttribute("errorMsg", "이미지 업로드 실패: " + e.getMessage());
                return "freeboard/form"; // 템플릿 경로
            }
        }

        freeboardService.save(freeboard);
        return "redirect:/freeboard/list";
    }


}