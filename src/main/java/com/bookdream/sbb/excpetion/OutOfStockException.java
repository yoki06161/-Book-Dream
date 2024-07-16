package com.bookdream.sbb.excpetion;

public class OutOfStockException extends RuntimeException {
	public OutOfStockException(String message) {
		super(message);
	}
}
