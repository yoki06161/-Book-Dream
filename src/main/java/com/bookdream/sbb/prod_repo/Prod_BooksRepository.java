package com.bookdream.sbb.prod_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// db랑 연결하는 리포지토리
// @Repository는 dao의 일부임을 알려줌? findall같은 메소드 쓰려면 써야함.
@Repository
// JpaRepository는 db만들고 지우고 수정할 수있는 메소드 제공
public interface Prod_BooksRepository extends JpaRepository<Prod_Books, Integer> {
}