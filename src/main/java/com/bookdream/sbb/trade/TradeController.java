package com.bookdream.sbb.trade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping("/list")
    public String listTrades(Model model) {
        List<Trade> trades = tradeService.getAllTrades();
        model.addAttribute("trades", trades);
        return "trade/list";
    }

    @GetMapping("/detail/{idx}")
    public String detailTrade(@PathVariable int idx, Model model) {
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
    public String createTrade(@ModelAttribute Trade trade) {
        tradeService.createTrade(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/edit/{idx}")
    public String editTradeForm(@PathVariable int idx, Model model) {
        Trade trade = tradeService.getTradeById(idx);
        model.addAttribute("trade", trade);
        return "trade/edit";
    }

    @PostMapping("/edit/{idx}")
    public String updateTrade(@PathVariable int idx, @ModelAttribute Trade trade) {
        tradeService.updateTrade(idx, trade);
        return "redirect:/trade/detail/" + idx;
    }

    @GetMapping("/delete/{idx}")
    public String deleteTrade(@PathVariable int idx) {
        tradeService.deleteTrade(idx);
        return "redirect:/trade/list";
    }
}