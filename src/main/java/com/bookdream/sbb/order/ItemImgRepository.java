package com.bookdream.sbb.order;

import com.bookdream.sbb.order.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
	List<ItemImg> findByItemIdOrderByAsc(Long itemId);
	ItemImg findByItemAndRepimgYn(Long itemId, String repimgYn);

}
