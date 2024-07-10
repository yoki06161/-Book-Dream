package com.bookdream.sbb;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookdream.sbb.prod_repo.Prod_Review;
import com.bookdream.sbb.prod_repo.review_repository;

@SpringBootTest
class BookDreamApplicationTests {

	@Autowired
	private review_repository re_repo;
	
	@Test
	void testJpa() {
		Prod_Review q1 = new Prod_Review();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setTest("sbb에 대해서 알고 싶습니다.");
        q1.setTimeIs(LocalDateTime.now());
        this.re_repo.save(q1);  // 첫번째 질문 저장

        Prod_Review q2 = new Prod_Review();
        q2.setSubject("sbb라나ㅏㅏㄴ?");
        q2.setTest("id는 자동생성일까? ");
        q2.setTimeIs(LocalDateTime.now());
        this.re_repo.save(q2);  // 첫번째 질문 저장
	}

}
