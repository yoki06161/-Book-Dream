package com.bookdream.sbb.pay;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdersRepository extends JpaRepository<Orders, Integer>{
	List<Orders> findByPay_id(String pay_id);
	List<Orders> findAll();
}