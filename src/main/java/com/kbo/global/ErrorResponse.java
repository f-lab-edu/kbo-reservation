package com.kbo.global;

public record ErrorResponse(
	int status, String message
) {
}
