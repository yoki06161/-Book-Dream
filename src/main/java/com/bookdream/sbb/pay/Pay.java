package com.bookdream.sbb.pay;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    // auto_increment
	private Integer pay_id;
	
	@Column(length = 20)
	private String email;
	
	@Column(length = 20, nullable = false)
	private String name;
	
	@Column(length = 11, nullable = false)
	private int phone;
	
	@Column(length = 8)
	private String pw;
	
	@Column(length = 5, nullable = false)
	private int post_code;
	
	@Column(length = 20, nullable = false)
	private String address;
	
	@Column(length = 20, nullable = false)
	private String detail_address;
	
	@Column(length = 20)
	private String request;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private String order_date;
	
	@Column(length = 50, nullable = false)
	private String total_price;
}
