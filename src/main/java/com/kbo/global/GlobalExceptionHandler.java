package com.kbo.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.exception.CustomNotExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CustomAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleAlreadyExistsException(CustomAlreadyExistsException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
			.body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
	}

	@ExceptionHandler(CustomNotExistsException.class)
	public ResponseEntity<ErrorResponse> handleNotExistsException(CustomNotExistsException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}
}
