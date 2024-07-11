package com.bookdream.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
		List<Prod_Review> plist = this.re_repo.findBySubjectLike("sbb%");
		Prod_Review r = plist.get(0);
		assertEquals("sbb가 무엇인가요?", r.getSubject());

	}

}
