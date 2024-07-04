package com.bookdream.sbb.trade;

import java.util.List;
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
        return tradeRepository.findAllByKeyword(kw, pageable);
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Trade getTradeById(int idx) {
        Optional<Trade> trade = tradeRepository.findById(idx);
        return trade.orElse(null);
    }

    public Trade createTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    public Trade updateTrade(int idx, Trade updatedTrade) {
        Optional<Trade> optionalTrade = tradeRepository.findById(idx);
        if (optionalTrade.isPresent()) {
            Trade trade = optionalTrade.get();
            trade.setTitle(updatedTrade.getTitle());
            trade.setPrice(updatedTrade.getPrice());
            trade.setWriter(updatedTrade.getWriter());
            trade.setIntro(updatedTrade.getIntro());
            trade.setPostdate(updatedTrade.getPostdate());
            trade.setId(updatedTrade.getId());
            return tradeRepository.save(trade);
        } else {
            return null;
        }
    }

    public void deleteTrade(int idx) {
        tradeRepository.deleteById(idx);
    }
}