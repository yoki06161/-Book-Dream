package com.bookdream.sbb.prod_repo;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	// 여기서 선언한 timeIs란 변수가 html에서 쓰인다. sql에는 time_is라 저장됐는데. 다른거인가?
	private LocalDateTime timeIs;
	
	// mappedBy값은 @ManyToOne에서 설정한 private Prod_Review review값.
	@OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE) 
    private List<Prod_RAnswer> a_List;

	
}
