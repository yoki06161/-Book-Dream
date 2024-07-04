package com.bookdream.sbb.prod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Service
@Getter
// 이거 클래스 명이 자바 클래스랑 같지 않아서 실행 안됐었음
public class Prod_crawling {

	private static String url = "https://www.aladin.co.kr/home/welcome.aspx";

	// 스프링시작할떄 실행되는 메소드지정
	public static List<Prod_Books> getc_Datas() throws IOException {
		List<Prod_Books> book_list = new ArrayList<>();
		// jsoup로 url에 연결
	    Document doc = Jsoup.connect(url).get();
	    
	    // 쿼리 선택하는거
	    Elements books_txt = doc.select("#w_thisMonth > div.swiper-wrapper > div > div.text > div.tit > a");
	    Elements books_img = doc.select("#w_thisMonth > div.swiper-wrapper > div > div.cover > a > img");
	    // div째로 출력되는거
		System.out.println("div째로 출력되는 img" + books_img);
	    
	    // 가져온 요소 넣기
	    for(Element title : books_txt) {
	    	
	    	Prod_Books books = Prod_Books.builder()
    			.book_title(title.select("a").text())
    			.book_img(books_img.attr("src"))
    			// 객체 생성 완료란뜻
    			.build();
	    
    	 book_list.add(books);
    	 // books에는 Prod_Books(book_title=제목, book_img=https://생략, book_price=0)식으로 저장됨
    	 System.out.println(books.getBook_title());
    	 System.out.println(books.getBook_img());
//    	 System.out.println(books.getBook_price());
	    }
	    
	    return book_list;
//	    // 텍스트 제목만 쭉 가로로 나열된다.
//	    System.out.println(books_txt.text());
	}
	
}
