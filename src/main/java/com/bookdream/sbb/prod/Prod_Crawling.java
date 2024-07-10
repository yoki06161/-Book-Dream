package com.bookdream.sbb.prod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
@Getter
// 이거 클래스 명이 자바 클래스랑 같지 않아서 실행 안됐었음
public class Prod_Crawling {

	// 빨라지는거라는데 모르겠음
	@Cacheable("bookList")
	// 상품 리스트 크롤링
	public static List<Prod_Books> getc_Datas() throws IOException {
		// 알라딘 베스트 셀러 링크
		String url = "https://www.aladin.co.kr/shop/common/wbest.aspx?BranchType=1&start=we";
		
		List<Prod_Books> book_list = new ArrayList<>();
		// jsoup로 url에 연결
	    Document doc = Jsoup.connect(url).get();
	    
	    // 쿼리 선택하는거
	    // Element는 태그를 뜻하는데 Elements는 모든 태그, Element는 첫번째 태그만을 인식한다. 즉 Elements는 리스트.
	    // 그러니까 밑의 Elements는 모든 #w_this.month>...>div.tit>a를 가져온단 뜻.
	    Elements books_txt = doc.select("#Myform > div > table > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > ul > li > a > b");
	    Elements books_img = doc.select("#Myform > div > table > tbody > tr > td:nth-child(2) > table > tbody > tr:nth-child(1) > td > div.cover_area > a > div > div > img.front_cover");
	    // span[class='']:nth-child(1) 이름이 지정되지 않은 클래스에서 1번째 아이
	    Elements books_price = doc.select("#Myform > div > table > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > ul > li > span[class='']:nth-child(1)");
	    Elements books_writer = doc.select("#Myform > div > table > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > ul > li:nth-child(3)");
	    Elements books_id = doc.select("#Myform > div.ss_book_box");
	    Elements books_sub = doc.select("#Myform > div > table > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > ul > li:nth-child(2) > span.ss_f_g2");
	    
	    // div째로 출력되는거
	    // 어째서인지 여기서 books_price.attr("itemid")은 값이 없다.
	    
		for (int i = 0; i < books_txt.size(); i++) {
			// book_txt길이만큼 실행후 그길이 만큼 Element에 저장
			// Element는 하나의 태그니까 title하나에 하나의 title을 넣고 for로 반복해서 넣겠단뜻
            Element title = books_txt.get(i);
            Element img = books_img.get(i);
            Element price = books_price.get(i);
            Element writer = books_writer.get(i);
            Element id = books_id.get(i);
//            Element sub = books_sub.get(i);
//            System.out.println(i + "번째 소제목 " + sub);
            
            Prod_Books books = Prod_Books.builder()
	            .book_title(title.text())
	            .book_img(img.attr("src"))
	            .book_price(price.text())
	            .book_writer(writer.text())
	            .book_id(id.attr("itemid"))
	            // 여기에 책의 가격 정보나 추가적인 정보를 가져와서 설정할 수 있음
	            .build();
            
    	 book_list.add(books);
    	 // books에는 Prod_Books(book_title=제목, book_img=https://생략, book_price=0)식으로 저장됨
//    	 System.out.println(books.getBook_title());
//    	 System.out.println(books.getBook_img());
//    	 System.out.println(books.getBook_price());
//    	 System.out.println(books.getBook_writer());
//    	 System.out.println("책 아이디" + books_id.get(i).text());
	    }
//		System.out.println(book_list);
	    return book_list;
	}
	
}