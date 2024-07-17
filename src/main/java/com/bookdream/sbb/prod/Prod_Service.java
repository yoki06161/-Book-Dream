package com.bookdream.sbb.prod;

import com.bookdream.sbb.prod_repo.*;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import javassist.SerialVersionUID;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

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
	
	// 모든 책 리스트 갖고오기. 안쓰이긴 하는데 혹시 모르니까.
	public List<Prod_Books> getAllBooks() {
		// prodRepository를 이용해 데이터 베이스에 저장된 모든 책을 찾음
		return prodRepository.findAll();
	}
	
	// 검색된 책들만 갖고 오기
	public List<Prod_Books> getSearchBooks(String kw) {
		Specification<Prod_Books> spec = search(kw);
		return prodRepository.findAll(spec);
	}
	
	// book_list의 책들을 모두 db에 저장함
	void saveBooks(List<Prod_Books> book_list) {		
		prodRepository.saveAll(book_list);
	}
	
	// 제품 상세보기. 받은 id에 따라 db에서 책 정보 조회
	public Prod_Books getProdBooks(Integer book_id) {
		// Optional임시 데이터 타입인듯. 무슨 데이터 타입이든 받아들이는
		// select * from prodRepository where id = book_id라 생각하자.
		Optional<Prod_Books> opb = this.prodRepository.findById(book_id);
		return opb.get();
	}
	

	// 검색
	// Specification은 요구사항을 명확히 설정?
	private Specification<Prod_Books> search(String kw) {
		return new Specification<>() {
		private static final long SerialVersionUID = 1L;
		
		@Override
		public Predicate toPredicate(Root<Prod_Books> books, CriteriaQuery<?> query, CriteriaBuilder cb) {
			query.distinct(true); // 중복을 제거
			// like로 검색
			return cb.or(cb.like(books.get("book_title"), "%" + kw + "%"),	// 제목
					cb.like(books.get("book_writer"), "%" + kw + "%"));	// 저자
		}
	};
	}
	
	
	// ###################리뷰
	// 리뷰 리스트 갖고오기. 책에 맞는 리뷰갖고오기
	public List<Prod_d_Review> getReview_List(Integer book_id) {
		List<Prod_d_Review> r_list = this.re_repo.findByBook(book_id);
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
}
