package com.bookdream.sbb;

public class DataNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1;
	
	public DataNotFoundException(String msg) {
		super(msg);
	}
}