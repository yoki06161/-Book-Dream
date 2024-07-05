package com.bookdream.sbb.trade;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;


@Service
@Getter
public class TradeCrawling {

    private static String url = "https://www.aladin.co.kr/home/welcome.aspx";

    @Autowired
    private TradeRepository tradeRepository;

    @PostConstruct
    public void crawlAndSaveBooks() {
        try {
            List<Trade> trades = scrapeBooks();
            tradeRepository.saveAll(trades);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Trade> scrapeBooks() throws IOException {
        List<Trade> trades = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();

        Elements books_title = doc.select("#w_thisMonth > div.swiper-wrapper > div > div.text > div.tit > a");
        Elements books_writer = doc.select("#w_thisMonth > div.swiper-wrapper > div > div.text > div.auth > a");
        Elements books_price = doc.select("#w_thisMonth > div.swiper-wrapper > div > div.text > div.price");

        // 데이터 사이즈 비교하여 가장 작은 사이즈에 맞추어 반복
        int minSize = Math.min(books_title.size(), Math.min(books_writer.size(), books_price.size()));
        for (int i = 0; i < minSize; i++) {
            Element title = books_title.get(i);
            Element writer = books_writer.get(i);
            Element price = books_price.get(i);

            Trade trade = new Trade();
            trade.setTitle(title.text());
            trade.setWriter(writer.text());
            trade.setPrice(parsePrice(price.text()));
            trade.setIntro(""); // 내용은 초기에 비워둠
            trade.setPostdate(LocalDateTime.now());

            trades.add(trade);
        }

        return trades;
    }

    private int parsePrice(String priceText) {
        // 숫자 이외의 문자를 제거하고 숫자만 추출하여 가격을 파싱
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(priceText);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            // 예외 처리 필요
            return 0; // 기본값 또는 에러 처리
        }
    }
}
