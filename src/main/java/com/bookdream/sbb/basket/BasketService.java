package com.bookdream.sbb.basket;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookdream.sbb.prod_repo.Prod_Books;
import com.bookdream.sbb.prod_repo.Prod_BooksRepository;

@Service
public class BasketService {
	
	@Autowired
	private BasketRepository basketRepository;
	private Prod_BooksRepository prodRepository;	
}
