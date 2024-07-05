package com.bookdream.sbb.trade;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

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
    public String createTradeForm(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/create";
    }

    @PostMapping("/create")
    public String createTrade(@ModelAttribute Trade trade, @RequestParam("image") MultipartFile image, RedirectAttributes redirectAttributes) {
        try {
            tradeService.createTrade(trade);
            redirectAttributes.addFlashAttribute("successMsg", "상품 등록 성공!!");
            return "redirect:/trade/list";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMsg", "상품 등록 실패: 이미지 업로드 중 오류 발생");
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
    public String updateTrade(@PathVariable("idx") int idx, @ModelAttribute Trade updatedTrade, RedirectAttributes redirectAttributes) {
        try {
            tradeService.updateTrade(idx, updatedTrade);
            redirectAttributes.addFlashAttribute("successMsg", "상품 수정 성공!!");
            return "redirect:/trade/detail/" + idx;
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMsg", "상품 수정 실패: 이미지 업로드 중 오류 발생");
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

