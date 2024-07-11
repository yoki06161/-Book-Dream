package com.bookdream.sbb.prod_repo;

import java.time.LocalDateTime;

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
public class Prod_Review {

	// @id는 중복 안되게하는것
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 열 길이조절
	@Column(length = 200)
	private String subject;
	
	// columnDefinition은 데이터 성격 정의
	@Column(columnDefinition = "TEXT")
	private String test;
	
	private LocalDateTime timeIs;
	
	
	
}
