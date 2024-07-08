package com.bookdream.sbb.trade;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookdream.sbb.trade.chat.ChatService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ChatService chatService;
    
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Trade> paging = tradeService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "trade/list";
    }

    @GetMapping("/detail/{idx}")
    public String detailTrade(@PathVariable("idx") int idx, Model model) {
        Trade trade = tradeService.getTradeById(idx);
        model.addAttribute("trade", trade);
        return "trade/detail";
    }
    
    @GetMapping("/create")
    public String createTradeForm(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("trade", new Trade());
        return "trade/create";
    }

    @PostMapping("/create")
    public String createTrade(@ModelAttribute Trade trade, @RequestParam("imageFile") MultipartFile imageFile, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String username = (String) session.getAttribute("username");
            trade.setId(username);

            if (!imageFile.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                String filePath = "C:/uploads/" + fileName;
                File destFile = new File(filePath);
                destFile.getParentFile().mkdirs();
                imageFile.transferTo(destFile);
                trade.setImage(fileName);
            }
            tradeService.createTrade(trade);
            redirectAttributes.addFlashAttribute("successMsg", "상품 등록 성공!!");
            return "redirect:/trade/list";
        } catch (Exception e) {
            System.out.println("Exception occurred while creating trade: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMsg", "상품 등록 실패: " + e.getMessage());
            return "redirect:/trade/create";
        }
    }

    @GetMapping("/edit/{idx}")
    public String editTradeForm(@PathVariable("idx") int idx, Model model) {
        Trade trade = tradeService.getTradeById(idx);
        model.addAttribute("trade", trade);
        return "trade/edit";
    }

    @PostMapping("/edit/{idx}")
    public String updateTrade(@PathVariable("idx") int idx, @ModelAttribute Trade updatedTrade, @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
        try {
            if (!imageFile.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                String filePath = "C:/uploads/" + fileName; // 파일이 저장될 경로 설정
                File destFile = new File(filePath);
                // 경로가 존재하지 않으면 디렉토리를 생성
                destFile.getParentFile().mkdirs();
                imageFile.transferTo(destFile);
                updatedTrade.setImage(fileName); // 업데이트된 Trade 엔티티에 이미지 파일명 저장
            }
            tradeService.updateTrade(idx, updatedTrade);
            redirectAttributes.addFlashAttribute("successMsg", "상품 수정 성공!!");
            return "redirect:/trade/detail/" + idx;
        } catch (Exception e) {
            // 예외 로그 출력
            System.out.println("Exception occurred while updating trade: " + e.getMessage());
            e.printStackTrace(); // 콘솔에 예외 출력
            redirectAttributes.addFlashAttribute("errorMsg", "상품 수정 실패: " + e.getMessage());
            return "redirect:/trade/edit/" + idx;
        }
    }

    @GetMapping("/delete/{idx}")
    public String deleteTrade(@PathVariable("idx") int idx, RedirectAttributes redirectAttributes) {
        tradeService.deleteTrade(idx);
        redirectAttributes.addFlashAttribute("successMsg", "상품 삭제 성공!!");
        return "redirect:/trade/list";
    }
    
    @GetMapping("/book")
    @ResponseBody
    public ResponseEntity<Trade> getBookInfo(@RequestParam("title") String title) {
        Trade trade = tradeService.getTradeByTitle(title);

        if (trade != null) {
            return new ResponseEntity<>(trade, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
