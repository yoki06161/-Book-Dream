package com.bookdream.sbb.prod;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

@Service
@Getter
// 이거 클래스 명이 자바 클래스랑 같지 않아서 실행 안됐었음
public class Prod_Crawling6 {

	// 빨라지는거라는데 모르겠음
	@Cacheable("bookList")
	// 상품 리스트 크롤링
	// !!!!!!!!!!!!!!!자동 스크롤 버전 근데 css를 못찾음
	public static void getc_Datas() {
		// 구글 드라이버 등록. 경로는 절대경로로 해서 수정필요.
		System.setProperty("webdriver.chrome.driver", "C:/Users/TJ/git/Book-Dream/driver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        // 이 히드네스 없애니까 됨. 혹은 스크롤내려야 뜨는 구조일지도
        options.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(options);

        try {
            String url = "https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=342594262";
            driver.get(url);

            // 자바스크립트 실행을 위한 인터페이스 설정
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // 웹 페이지 끝까지 스크롤 다운
            js.executeScript("window.scrollTo(0, 1200)");
            
            // 명시적 대기 설정 (최대 5초 기다림)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement authorInfoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#Ere_prod_allwrap > div.Ere_prod_middlewrap > div:nth-child(17) > div.Ere_prod_mconts_R")));

            // 요소를 찾았을 때 작업 수행
            String authorInfoText = authorInfoElement.getText();
            System.out.println("헤에에에에에게ㅔ겍: " + authorInfoText);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
	}
	
}
