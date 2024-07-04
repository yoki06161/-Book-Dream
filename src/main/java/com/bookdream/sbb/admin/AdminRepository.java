package com.bookdream.sbb.admin;

import java.util.List;

public interface AdminRepository extends JpaRespoitory<Review, Integer> {
	Review findReview(String review);
	Review findReviewAndContent(String review, String content);
	List<Review> findByReviewLike(String review);
}
