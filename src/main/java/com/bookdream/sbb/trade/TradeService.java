package com.bookdream.sbb.trade;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public Page<Trade> getList(int page, String kw) {
        Pageable pageable = PageRequest.of(page, 10);
        return tradeRepository.findAllByOrderByPostdateDesc(pageable);
    }

    public Trade getTradeById(int idx) {
        Optional<Trade> trade = tradeRepository.findById(idx);
        return trade.orElse(null);
    }

    public void createTrade(Trade trade) {
        if (trade.getPostdate() == null) {
            trade.setPostdate(LocalDateTime.now());
        }
        tradeRepository.save(trade);
    }

    public Trade updateTrade(int idx, Trade updatedTrade) {
        Optional<Trade> optionalTrade = tradeRepository.findById(idx);
        if (optionalTrade.isPresent()) {
            Trade trade = optionalTrade.get();
            trade.setTitle(updatedTrade.getTitle());
            trade.setPrice(updatedTrade.getPrice());
            trade.setWriter(updatedTrade.getWriter());
            trade.setIntro(updatedTrade.getIntro());
            return tradeRepository.save(trade);
        } else {
            return null;
        }
    }

    public void deleteTrade(int idx) {
        tradeRepository.deleteById(idx);
    }
}
