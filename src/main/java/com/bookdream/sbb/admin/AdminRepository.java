//package com.bookdream.sbb.admin;
//
//import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface AdminRepository extends JpaRepository<Review, Integer> {
//   Review findReview(String review);
//   Review findReviewAndContent(String review, String content);
//   List<Review> findByReviewLike(String review);
//}