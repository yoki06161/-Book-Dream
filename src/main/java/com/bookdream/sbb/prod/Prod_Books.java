package com.bookdream.sbb.prod;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter

public class Prod_Books {
	
	private String book_title;
	private String book_img;
	private int book_price;
}
