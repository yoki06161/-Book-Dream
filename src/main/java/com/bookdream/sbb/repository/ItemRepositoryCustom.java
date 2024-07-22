package com.bookdream.sbb.repository;

import com.bookdream.sbb.basket.Basket;


public interface ItemRepositoryCustom {
	Page<Item> getAdminItemPage(Pageable pageable);
}
