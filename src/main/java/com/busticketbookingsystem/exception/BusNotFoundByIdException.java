package com.busticketbookingsystem.exception;

public class BusNotFoundByIdException extends RuntimeException {

	private String message;

	public BusNotFoundByIdException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
