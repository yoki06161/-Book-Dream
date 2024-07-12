package com.bookdream.sbb.prod;

import com.bookdream.sbb.prod_repo.*;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

// final쓸때 씀
@RequiredArgsConstructor
@Service
public class Prod_Service {
	@Autowired
	private final ProdBooksRepository prodRepository;
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
		Optional<Prod_Books> opb = this.prodRepository.findById(book_id);
		return opb.get();
	}
	
	// 리뷰 리스트 갖고오기
	public List<Prod_d_Review> getReview_List() {
		return this.re_repo.findAll();
	}
	
	// 리뷰 쓰기
	public void Write_Review(String review) {
		Prod_d_Review pr = new Prod_d_Review();
		pr.setReview(review);
		pr.setTime(LocalDateTime.now());
		this.re_repo.save(pr);
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
