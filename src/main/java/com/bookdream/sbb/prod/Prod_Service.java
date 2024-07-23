package com.bookdream.sbb.prod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bookdream.sbb.prod_repo.Prod_Books;
import com.bookdream.sbb.prod_repo.Prod_BooksRepository;
import com.bookdream.sbb.prod_repo.Prod_RArepository;
import com.bookdream.sbb.prod_repo.Prod_RErepository;
import com.bookdream.sbb.prod_repo.Prod_Score;
import com.bookdream.sbb.prod_repo.Prod_ScoreRepository;
import com.bookdream.sbb.prod_repo.Prod_d_Answer;
import com.bookdream.sbb.prod_repo.Prod_d_Review;
import com.bookdream.sbb.user.Member;
import com.bookdream.sbb.user.MemberRepository;
import com.bookdream.sbb.user.SiteUser;
import com.bookdream.sbb.user.UserRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

// final쓸때 씀
@RequiredArgsConstructor
@Service
public class Prod_Service {
	@Autowired
	private final Prod_BooksRepository prodRepository;
	private final Prod_RErepository re_repo;
	private final Prod_RArepository ra_repo;
	private final Prod_ScoreRepository sco_repo;
		
	// 두목님꺼. 일반로그인
	private final UserRepository user_repo;
	private final MemberRepository mem_repo;
	
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
		Optional<Prod_Books> opb = prodRepository.findById(book_id);
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
	
//	// 별점 테스트
//	 public void score_vote(Prod_Books books, SiteUser user) {
//		 // 포터 값을 불러와서 거기에 유저 명을 입력하는듯.
//		books.getVoter().add(user);
//        prodRepository.save(books);
//    }
	
	
	// ###################리뷰
	// 리뷰 리스트 갖고오기. 책에 맞는 리뷰갖고오기
	public List<Prod_d_Review> getReview_List(Integer book_id) {
		List<Prod_d_Review> r_list = re_repo.findByBook(book_id);
		return r_list;
	}
	
	// 리뷰 쓰기
	public void Write_Review(String review, Integer book_id, String user) {
		Prod_d_Review pr = new Prod_d_Review();
		pr.setReview(review);
		pr.setBook(book_id);
		pr.setTime(LocalDate.now());
		pr.setUser(user);
		
		re_repo.save(pr);
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
		
		ra_repo.save(pa);
	}
	
    // 리뷰 및 답글에 로그인한 사용자 닉네임 넣기.
    public String getUserName(String user) {
    	// 로그인할떄 email값이 들어와서 email = user인 값을 db로 찾는다. where email = user 
    	Optional<SiteUser> user_email = user_repo.findByEmail(user);
    	// 위의건 그냥 로그인. 밑의건 구글, 카카오 로그인.
    	Member mem_email = mem_repo.findByLoginId(user);
//    	System.out.println("###########################서비스 user값 " + user);
//    	System.out.println("###########################서비스 mem_email값 " + mem_email);
    	
    	// ispresent는 optional에서만 되서 mem_email은 못씀.
    	if(user_email.isPresent()) {
    		// username.get()이라고만 쓰면 com.bookdream.sbb.user.SiteUser@67b61fcd식으로 경로만 뜬다.
        	// .get()모두.get원하는 칼럼명(). get원하는 칼럼명()을 해야 그 칼럼의 값을 갖고옴.
    		// SiteUser의 username칼럼 자체가 string 형태라 stieuser user_name = 으로 못불러온다.
    		String user_name = user_email.get().getUsername();

//    	    System.out.println("###########################서비스 user_name값" + user_name);
//        	System.out.println("###########################서비스 user_email.get()값" + user_email.get());
        	
    		return user_name;
    		
    	} else if(mem_email != null) {
    		String mem_name = mem_email.getName();
//    		System.out.println("###########################서비스 mem_name값" + mem_name);
    		
    		return mem_name;
    		
    	} else {
    		// 이제 나올리 없음.
    		return "익명";
    	}
	}
    
    // 별점 테스트 이메일 불러오기
    public String getUser(String user) {
		Optional<SiteUser> siteUser = user_repo.findByEmail(user);
		Member mem_email = mem_repo.findByLoginId(user);
//		System.out.println("!!!!!!!!!!!!!!!일반 유저 이메일 " + siteUser);
//		System.out.println("!!!!!!!!!!!!!!!카카오 유저 이메일 " + mem_email);
		
		if(siteUser.isPresent()) {
			String user_name = siteUser.get().getEmail();
//			System.out.println("!!!!!!!!!!!!!!!리턴 일반 유저 이메일 " + user_name);
    		return user_name;
		} else if(mem_email != null) {
    		String mem_name = mem_email.getLoginId();
//    		System.out.println("!!!!!!!!!!!!!!!리턴 카카오 유저 이메일 " + mem_name);
    		return mem_name;
    	} else {
    		// 이제 나올리 없음.
    		return "익명";
    	}
	}
    
    // 별점 넣기
    public void set_score(Integer book, String user, Integer score) {
    	System.out.println("스코어 연결");
    	Prod_Score sc = new Prod_Score();
    	sc.setBook(book);
    	sc.setUser(user);
    	sc.setScore(score);
    	
    	sco_repo.save(sc);
	}
    
    
    
    
    
}
