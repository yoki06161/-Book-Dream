package com.bookdream.sbb.prod_repo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Prod_d_Answer {

	// 답글 기본아이디
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 유저 이름
	@Column(length = 200)
	private String user;
	
	// 답변
	@Column(columnDefinition = "TEXT")
	private String answer;
	
	// 작성 시간
	private LocalDate time;
	
	// 댓글이랑 연결. 댓글이 부모, 답변이 자식. 매니가 답변, 원이 댓글
	// 댓글 아이디랑 연결된다.
	@ManyToOne
	private Prod_d_Review review;
}
