package com.bookdream.sbb.basket;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookdream.sbb.prod_repo.Prod_Books;
import com.bookdream.sbb.prod_repo.Prod_BooksRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasketService {
	
	@Autowired
	private BasketRepository basketRepository;
	private Prod_BooksRepository prodRepository;
	
    public void saveBasketItems(List<Map<String, Object>> sessionData) {
        for (Map<String, Object> data : sessionData) {
            Basket basket = new Basket();
            basket.setEmail((String) data.get("email"));
            basket.setBook_id((Integer) data.get("book_id"));
            basket.setCount((Integer) data.get("count"));
            basket.setCount_price((String) data.get("count_price"));
            basketRepository.save(basket);
        }
    }
}
