package com.bookdream.sbb.basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
	
	@Autowired
	private BasketRepository basketRepository;
	
//	public Long getCountBasketsById(String id) {
//		return basketRepository.countBasketsById(id);
//	}
	
}
