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
public class Prod_Crawling2 {

	// 빨라지는거라는데 모르겠음
	@Cacheable("bookList")
	// 상품 리스트 크롤링
	public static List<Prod_Books> getc_Datas() throws IOException {
		// 알라딘 베스트 셀러 링크
		String url = "https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=342594262";
		
		List<Prod_Books> book_list = new ArrayList<>();
		// jsoup로 url에 연결
	    Document doc = Jsoup.connect(url).get();
	    
	    // 쿼리 선택하는거
	    // Element는 태그를 뜻하는데 Elements는 모든 태그, Element는 첫번째 태그만을 인식한다. 즉 Elements는 리스트.
	    // 그러니까 밑의 Elements는 모든 #w_this.month>...>div.tit>a를 가져온단 뜻.
	    Elements books_txt = doc.select("#Ere_prod_allwrap > div.Ere_prod_middlewrap > div:nth-child(17) > div.Ere_prod_mconts_R");
	    Elements books_txt2 = doc.select("#Ere_prod_allwrap > div.Ere_prod_middlewrap > div:nth-child(17)");
	    
	    // div째로 출력되는거
	    // 어째서인지 여기서 books_price.attr("itemid")은 값이 없다.
	    System.out.println("책소개문구 "+books_txt);
	    System.out.println("책소개문구2 "+books_txt2);
	    // 문구 2출력시 <div class="pContent" id="K312932961_AuthorInfo"></div>가 나옴.
	    
		for (int i = 0; i < books_txt.size(); i++) {
			// book_txt길이만큼 실행후 그길이 만큼 Element에 저장
			// Element는 하나의 태그니까 title하나에 하나의 title을 넣고 for로 반복해서 넣겠단뜻
            Element title = books_txt.get(i);
            
            Prod_Books books = Prod_Books.builder()
	            .book_title(title.text())
	            .build();
	    
    	 book_list.add(books);
    	 // books에는 Prod_Books(book_title=제목, book_img=https://생략, book_price=0)식으로 저장됨
    	 System.out.println("최종 소개문구 " + books.getBook_title());
	    }
//		System.out.println(book_list);
	    return book_list;
	}
	
}
