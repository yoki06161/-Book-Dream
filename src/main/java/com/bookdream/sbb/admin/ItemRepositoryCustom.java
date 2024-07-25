package com.bookdream.sbb.admin;

import com.bookdream.sbb.basket.Basket;


public interface ItemRepositoryCustom {
	Page<Item> getAdminItemPage(Pageable pageable);
}
