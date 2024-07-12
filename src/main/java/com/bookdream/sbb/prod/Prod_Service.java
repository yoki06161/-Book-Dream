package com.bookdream.sbb.prod;

import com.bookdream.sbb.prod_repo.*;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

// final쓸때 씀
@RequiredArgsConstructor
@Service
public class Prod_Service {
	@Autowired
	private final ProdBooksRepository prodRepository;
	private final review_repository re_repo;
	
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
	
	// 내꺼. 질문 리스트 갖고오는거
	public List<Prod_Review> get_t_list() {
		return this.re_repo.findAll();
	}
	
	
	public Prod_Review get_t_detail(Integer id) throws DataNotFound {
		Optional<Prod_Review> op = this.re_repo.findById(id);
		if(op.isPresent()) {
			return op.get();
		} else {
			throw new DataNotFound("질문이 없음");
		}
	}
	
}
