package com.bookdream.sbb.prod;

import com.bookdream.sbb.prod_repo.ProdBooksRepository;
import com.bookdream.sbb.prod_repo.Prod_Books;
import com.bookdream.sbb.prod_repo.*;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class Prod_Service {
	@Autowired
	private final ProdBooksRepository prodRepository;
	
	public List<Prod_Books> getAllBooks() {
		return prodRepository.findAll();
	}
	
	void saveBooks(List<Prod_Books> book_list) {		
		prodRepository.saveAll(book_list);
	}
}
