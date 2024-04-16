package com.busticketbookingsystem.exception;

public class DestinationCannotBeAlteredException extends RuntimeException {

	private String message;

	public DestinationCannotBeAlteredException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
