package com.busticketbookingsystem.exception;

public class SeatNotFoundBySeatNoAndBus extends RuntimeException {

	private String message;

	public SeatNotFoundBySeatNoAndBus(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
