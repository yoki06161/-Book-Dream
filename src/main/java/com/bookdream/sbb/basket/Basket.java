package com.bookdream.sbb.basket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table (name = "basket")
@Getter
@Setter
@Entity
public class Basket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    // auto_increment
	private Long idx;
	
	@Column(length = 20)
	private String id;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private String created_at;
	
	@Column
	private int book_id;
}