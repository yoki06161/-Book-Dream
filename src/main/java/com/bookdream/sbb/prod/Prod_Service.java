package com.bookdream.sbb.prod;

import com.bookdream.sbb.prod_repo.*;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

// final쓸때 씀
@RequiredArgsConstructor
@Service
public class Prod_Service {
	@Autowired
	private final Prod_BooksRepository prodRepository;
	private final Prod_RErepository re_repo;
	private final Prod_RArepository ra_repo;
	
	public List<Prod_Books> getAllBooks() {
		// prodRepository를 이용해 데이터 베이스에 저장된 모든 책을 찾음
		return prodRepository.findAll();
	}
	
	// book_list의 책들을 모두 db에 저장함
	void saveBooks(List<Prod_Books> book_list) {		
		prodRepository.saveAll(book_list);
	}
	
	// 받은 id에 따라 db에서 책 정보 조회
	public Prod_Books getProdBooks(Integer book_id) {
		// Optional임시 데이터 타입인듯. 무슨 데이터 타입이든 받아들이는
		// select * from prodRepository where id = book_id라 생각하자.
		Optional<Prod_Books> opb = this.prodRepository.findById(book_id);
		return opb.get();
	}
	
	// ###################리뷰
	// 리뷰 리스트 갖고오기. 책에 맞는 리뷰갖고오기
	public List<Prod_d_Review> getReview_List(Integer book_id) {
		List<Prod_d_Review> r_list = this.re_repo.findByBook(book_id);
		System.out.println("#################" + this.re_repo.findAll());
		return r_list;
	}
	
	// 리뷰 쓰기
	public void Write_Review(String review, Integer book_id) {
		Prod_d_Review pr = new Prod_d_Review();
		pr.setReview(review);
		pr.setBook(book_id);
		pr.setTime(LocalDate.now());
		this.re_repo.save(pr);
	}
	
	// 리뷰 답글 갖고오기.
	public List<Prod_d_Answer> getAnswer_List() {
		List<Prod_d_Answer> a_list = ra_repo.findAll();
		return a_list;
	}
	
	// 리뷰 답글 쓰기
	public void Write_Answer(Prod_d_Review review_id, String content) {
		Prod_d_Answer pa = new Prod_d_Answer();
		pa.setAnswer(content);
		pa.setTime(LocalDate.now());
		pa.setReview(review_id);
		
		this.ra_repo.save(pa);
	}
	

	// 연습용으로 한것들
	// !!!!!!!!!!!!!!!!!!!내꺼. 질문 리스트 갖고오는거
//	public List<Prod_d_Review> get_t_list() {
//		// select * from 테이블이랑 같은거임
//		return this.re_repo.findAll();
//	}
	
	// 아이디 값에 따라 질문 갖고오기
	public Prod_d_Review get_t_detail(Integer id) throws DataNotFound {
		Optional<Prod_d_Review> op = this.re_repo.findById(id);
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new DataNotFound("질문이 없음");
		}
	}
	
	public void create(Prod_d_Review pr, String cont) {
		Prod_d_Answer pra = new Prod_d_Answer();
//		pra.setContent2(cont);
//		pra.setA_time(LocalDateTime.now());
//		pra.setReview(pr);
		this.ra_repo.save(pra);
	}


	

	
}
