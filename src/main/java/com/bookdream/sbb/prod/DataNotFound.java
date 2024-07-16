package com.bookdream.sbb.prod;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFound extends Exception {
	private static final long s_version = 1L;
	public DataNotFound(String message) {
		super(message);
	}
}