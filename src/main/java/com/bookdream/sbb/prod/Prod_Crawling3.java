package com.bookdream.sbb.prod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
@Getter
// 이거 클래스 명이 자바 클래스랑 같지 않아서 실행 안됐었음
public class Prod_Crawling3 {

	// 빨라지는거라는데 모르겠음
	@Cacheable("bookList")
	// 상품 리스트 크롤링
	public static void getc_Datas() throws IOException {
		System.out.println("어디되나 보자");
		// WebDriver 설정
        WebDriver driver = new ChromeDriver();

		// 알라딘 베스트 셀러 링크
		String url = "https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=342594262";
		// 제목 가져오기
        WebElement titleElement = driver.findElement(By.cssSelector("#Ere_prod_allwrap > div.Ere_prod_middlewrap"));
        String title = titleElement.getText();
        System.out.println("책 제목: " + title);

        // 저자 가져오기
        WebElement authorElement = driver.findElement(By.cssSelector("#K312932961_AuthorInfo"));
        String author = authorElement.getText();
        System.out.println("저자 정보: " + author);

        // WebDriver 종료
        driver.quit();
	}
	
}
