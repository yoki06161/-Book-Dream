package com.bookdream.sbb.prod;

import com.bookdream.sbb.prod_repo.*;
import com.bookdream.sbb.user.SiteUser;
import com.bookdream.sbb.user.UserRepository;

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
	
	// 두목님꺼 테스트
	private final UserRepository user_repo;
	
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
		System.out.println("##############opb는 ? " + opb);
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
	public void Write_Review(String review, Integer book_id, String user) {
		Prod_d_Review pr = new Prod_d_Review();
		pr.setReview(review);
		pr.setBook(book_id);
		pr.setTime(LocalDate.now());
		pr.setUser(user);
		
		this.re_repo.save(pr);
	}
	
	// 리뷰 답글 갖고오기.
	public List<Prod_d_Answer> getAnswer_List() {
		List<Prod_d_Answer> a_list = ra_repo.findAll();
		return a_list;
	}
	
	// 리뷰 답글 쓰기
	public void Write_Answer(Prod_d_Review review_id, String content, String user) {
		Prod_d_Answer pa = new Prod_d_Answer();
		pa.setAnswer(content);
		pa.setTime(LocalDate.now());
		pa.setReview(review_id);
		pa.setUser(user);
		
		this.ra_repo.save(pa);
	}
	
	// 두목님꺼 테스트
	// 내가 추가한 사용자 정보 갖고오기
    public List<SiteUser> getBoss_d() {
    	List<SiteUser> s_list = user_repo.findAll();
		return s_list;
	}
    
    // 테스트 2
    public String getBoss_user(String user) {
    	// 로그인할떄 email값이 들어와서 email = user인 값을 db로 찾는다. where email = user 
    	Optional<SiteUser> user_email = user_repo.findByEmail(user);
    	
    	if(user_email.isPresent()) {
    		// username.get()이라고만 쓰면 com.bookdream.sbb.user.SiteUser@67b61fcd식으로 경로만 뜬다.
        	// .get()모두.get원하는 칼럼명(). get원하는 칼럼명()을 해야 그 칼럼의 값을 갖고옴.
    		// SiteUser의 username칼럼 자체가 string 형태라 stieuser user_name = 으로 못불러온다.
    		String user_name = user_email.get().getUsername();

    	    System.out.println("###########################서비스 user값 " + user);
    	    System.out.println("###########################서비스 user_name값" + user_name);
        	System.out.println("###########################서비스 user_email.get()값" + user_email.get());
        	
    		return user_name;
    	} else {
    		return "익명";
    	}
	}
}
