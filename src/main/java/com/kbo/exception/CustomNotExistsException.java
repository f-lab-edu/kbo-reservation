package com.kbo.exception;

public class CustomNotExistsException extends RuntimeException {
	public CustomNotExistsException(String message) {
		super(message);
	}
}
