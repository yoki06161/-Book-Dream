package com.bookdream.sbb.prod_repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Prod_RErepository extends JpaRepository<Prod_d_Review, Integer>{

	Optional<Prod_d_Review> findByBook(Integer book_id);
//	Prod_EReview findBySubject(String subject);
//	Prod_EReview findBySubjectAndTest(String subject, String test);
//	List<Prod_EReview> findBySubjectLike(String subject);
}
