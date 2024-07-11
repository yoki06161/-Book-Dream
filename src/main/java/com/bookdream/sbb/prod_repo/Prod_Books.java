package com.bookdream.sbb.prod_repo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@Entity

public class Prod_Books {
	//인덱스키
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer book_id;

	
	// 제목
	@Column(length=100)
	private String book_title;
	
	// 사진
	@Column(length=100)
	private String book_img;
	
	// 가격
	@Column(length=50)
	private String book_price;
	
	// 책 정보(저자)
	@Column(length=50)
	private String book_writer;
	
	@Column(length=6000)
	private String book_intro;
}