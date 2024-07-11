package com.bookdream.sbb.prod_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdBooksRepository extends JpaRepository<Prod_Books, Integer> {
	
}