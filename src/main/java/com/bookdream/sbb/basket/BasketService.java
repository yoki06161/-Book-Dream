package com.bookdream.sbb.basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    @Transactional
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
    
    @Transactional
    public void deleteBasketItems(Integer book_id, String email) {
        basketRepository.deleteByBookIdAndEmail(book_id, email);
    }

//	public List<Basket> getItemsByEmail(String email) {
//		return basketRepository.getItemsByEmail(email);
//	}
}