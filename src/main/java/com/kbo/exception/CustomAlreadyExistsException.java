package com.kbo.exception;

public class CustomAlreadyExistsException extends RuntimeException {
	public CustomAlreadyExistsException(String message) {
		super(message);
	}
}
