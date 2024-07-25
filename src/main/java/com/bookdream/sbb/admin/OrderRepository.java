package com.bookdream.sbb.admin;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.criteria.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query("select o from Order o" +
			"where o.member.email = :email" +
			"order by o.orderDate desc"
	)
	// 현재 로그인한 사용자의 주문 데이터 조회
	List<Order> findOrders(@Param("email") String email, Pageable pageable);
	
	@Query("select count(o) from Order o " +
			"where o.member.email = email"
	)
	// 현재 로그인한 회원의 주문 개수가 몇개인지 조회
	Long countOrder(@Param("email") String email);
}
