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
public class Prod_Crawling2 {

	// 빨라지는거라는데 모르겠음
	@Cacheable("bookList")
	// 상품 리스트 크롤링
	public static String getc_Datas(String book_id) {
		// 구글 드라이버 등록. 경로는 절대경로로 해서 수정필요.
		System.setProperty("webdriver.chrome.driver", "C:/Users/TJ/git/Book-Dream/driver/chromedriver.exe");
		
        ChromeOptions c_option = new ChromeOptions();
        // 새창을 숨김. 그냥 --headless라쓰면 오류떠서 =new같이 쓴다.
        c_option.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(c_option);
        String detail_txt = null;
        
        try {
        	// 연결할 url. book_id는 선택한 id에 맞춰 사이트 크롤링
            String url = "https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=";
            url += book_id;
            driver.get(url);

            // 스크롤 내리기용 자바스크립트
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // 웹페이지 1200까지 스크롤 내림. 원본 사이트가 js로 스크롤 내려야 내용이 나오게 돼있어서.
            js.executeScript("window.scrollTo(0, 1200)");
            
            // 페이지 실행시 로딩 최대 5초 기다리게
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // 내용 넣기
            // Element랑 같은건데 조건문이 가능한듯. 이건 wait.until이라고 조건 맞췄고. until은 기다리는거. ExpectedCondition은 조건을 나타내는 인터페이스. visibilityOfElementLocated는 요소가 화면에 보일때까지 기다리는거.
            // div:nth-child(17)때문에 17번째에 없는건 그냥 오류뜨는데 그렇다고 17을 없애면 다른 div들 이름이 겹쳐서 첫번째꺼가 출력된다...
            WebElement book_detail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#Ere_prod_allwrap > div.Ere_prod_middlewrap > div:nth-child(17) > div.Ere_prod_mconts_R")));

            // 요소를 찾았을 때 작업 수행
            detail_txt = book_detail.getText();
            
//            Prod_Books books = Prod_Books.builder().book_detail(detail_txt).build();
            
            System.out.println("책소개: " + detail_txt);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
		return detail_txt;
	}
	
}
