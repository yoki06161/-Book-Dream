package com.bookdream.sbb.prod_repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface review_repository extends JpaRepository<Prod_Review, Integer>{
	Prod_Review findBySubject(String subject);
	Prod_Review findBySubjectAndTest(String subject, String test);
	List<Prod_Review> findBySubjectLike(String subject);
}
