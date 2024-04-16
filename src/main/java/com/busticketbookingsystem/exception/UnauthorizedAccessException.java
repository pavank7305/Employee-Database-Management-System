package com.busticketbookingsystem.exception;

public class UnauthorizedAccessException extends RuntimeException {

	private String message;

	public UnauthorizedAccessException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
