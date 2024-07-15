package com.bookdream.sbb.prod_repo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@Entity

//@Builder 어노테이션과 함께 사용할 경우, @NoArgsConstructor 및 @AllArgsConstructor 어노테이션을 추가하는 것이 좋습니다.
// -> 기본생성자 생성
@NoArgsConstructor
@AllArgsConstructor

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
	
	// 책소개
	@Column(length=6000)
	private String book_intro;
	
	// 책 장르
	@Column(length = 100)
	private String book_genre;
}