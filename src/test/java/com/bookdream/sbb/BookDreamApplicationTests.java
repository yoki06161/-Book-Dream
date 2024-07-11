package com.bookdream.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		Optional<Prod_Review> op = this.re_repo.findById(1);
		assertTrue(op.isPresent());
		Prod_Review p = op.get();
		p.setSubject("제목 수정 테스트");
		this.re_repo.save(p);

	}

}
