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
	private Long order_id;
	
	@Column(length = 20)
	private String name;
	
	@Column(length = 11)
	private int phone;
	
	@Column(length = 8)
	private String pw;
	
	@Column(length = 5)
	private int postcode;
	
	@Column(length = 20)
	private String address;
	
	@Column(length = 20)
	private String detailAddress;
	
	@Column(length = 20)
	private String request;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private String orderdate;
	
	@Column
	private int book_id;
}