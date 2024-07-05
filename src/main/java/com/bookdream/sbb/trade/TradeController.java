//package com.bookdream.sbb.trade;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequestMapping("/trade")
//public class TradeController {
//
//    @Autowired
//    private TradeService tradeService;
//
//    @GetMapping("/list")
//    public String list(Model model,@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
//        Page<Trade> paging = tradeService.getList(page, kw);
//        model.addAttribute("paging", paging);
//        model.addAttribute("kw", kw);
//        return "trade/list";
//    }
//
//    @GetMapping("/detail/{idx}")
//    public String detailTrade(@PathVariable int idx, Model model) {
//        Trade trade = tradeService.getTradeById(idx);
//        model.addAttribute("trade", trade);
//        return "trade/detail";
//    }
//
//    @GetMapping("/create")
//    public String createTradeForm(Model model) {
//        model.addAttribute("trade", new Trade());
//        return "trade/create";
//    }
//
//    @PostMapping("/create")
//    public String createTrade(@ModelAttribute Trade trade) {
//        tradeService.createTrade(trade);
//        return "redirect:/trade/list";
//    }
//
//    @GetMapping("/edit/{idx}")
//    public String editTradeForm(@PathVariable int idx, Model model) {
//        Trade trade = tradeService.getTradeById(idx);
//        model.addAttribute("trade", trade);
//        return "trade/edit";
//    }
//
//    @PostMapping("/edit/{idx}")
//    public String updateTrade(@PathVariable int idx, @ModelAttribute Trade trade) {
//        tradeService.updateTrade(idx, trade);
//        return "redirect:/trade/detail/" + idx;
//    }
//
//    @GetMapping("/delete/{idx}")
//    public String deleteTrade(@PathVariable int idx) {
//        tradeService.deleteTrade(idx);
//        return "redirect:/trade/list";
//    }
//}