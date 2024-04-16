package com.busticketbookingsystem.util;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.busticketbookingsystem.exception.BusDeletedException;
import com.busticketbookingsystem.exception.BusNotFoundByIdException;
import com.busticketbookingsystem.exception.DestinationCannotBeAlteredException;
import com.busticketbookingsystem.exception.RideCannotBeCreatedForStartDateTime;
import com.busticketbookingsystem.exception.RideDeletedException;
import com.busticketbookingsystem.exception.RideNotFoundByIdException;
import com.busticketbookingsystem.exception.SeatNotFoundBySeatNoAndBus;
import com.busticketbookingsystem.exception.UnauthorizedAccessException;
import com.busticketbookingsystem.exception.UserNotFoundByIdException;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ErrorStructure errorStructure;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ObjectError> allErrors = ex.getAllErrors();
		HashMap<String, String> errors = new HashMap<>();
		for (ObjectError error : allErrors) {
			String message = error.getDefaultMessage();
			FieldError fieldError = (FieldError) error;
			String fieldName = fieldError.getField();
			errors.put(fieldName, message);
		}
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(SeatNotFoundBySeatNoAndBus.class)
	ResponseEntity<ErrorStructure> handleStudentNotFoundByIdException(SeatNotFoundBySeatNoAndBus ex) {
		errorStructure.setStatus(HttpStatus.NOT_FOUND.value());
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Seat Not Found With Given Seat No and Bus");
		return new ResponseEntity<ErrorStructure>(errorStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnauthorizedAccessException.class)
	ResponseEntity<ErrorStructure> handleUnAuthorizedAccessException(UnauthorizedAccessException ex) {
		errorStructure.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("User is not an agent");
		return new ResponseEntity<ErrorStructure>(errorStructure, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BusNotFoundByIdException.class)
	ResponseEntity<ErrorStructure> handleBusNotFoundByIdException(BusNotFoundByIdException ex) {
		errorStructure.setStatus(HttpStatus.NOT_FOUND.value());
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Bus Not Found With Given Bus id");
		return new ResponseEntity<ErrorStructure>(errorStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BusDeletedException.class)
	ResponseEntity<ErrorStructure> handleBusDeletedException(BusDeletedException ex) {
		errorStructure.setStatus(HttpStatus.NOT_FOUND.value());
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Bus Not Found With Given Bus id");
		return new ResponseEntity<ErrorStructure>(errorStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DestinationCannotBeAlteredException.class)
	ResponseEntity<ErrorStructure> handleDestinationCannotBeAlteredException(DestinationCannotBeAlteredException ex) {
		errorStructure.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Destination cannot be altered");
		return new ResponseEntity<ErrorStructure>(errorStructure, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(RideNotFoundByIdException.class)
	ResponseEntity<ErrorStructure> handleRideNotFoundByIdException(RideNotFoundByIdException ex) {
		errorStructure.setStatus(HttpStatus.NOT_FOUND.value());
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Ride Not Found With Given Ride id");
		return new ResponseEntity<ErrorStructure>(errorStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotFoundByIdException.class)
	ResponseEntity<ErrorStructure> handleUserNotFoundByIdException(UserNotFoundByIdException ex) {
		errorStructure.setStatus(HttpStatus.NOT_FOUND.value());
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("User Not Found With Given user id");
		return new ResponseEntity<ErrorStructure>(errorStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RideDeletedException.class)
	ResponseEntity<ErrorStructure> handleRideDeletedException(RideDeletedException ex) {
		errorStructure.setStatus(HttpStatus.NOT_FOUND.value());
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Ride Not Found With Given Ride id");
		return new ResponseEntity<ErrorStructure>(errorStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RideCannotBeCreatedForStartDateTime.class)
	ResponseEntity<ErrorStructure> handleRideCannotBeCreatedForStartDateTime(RideCannotBeCreatedForStartDateTime ex) {
		errorStructure.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Ride Failed to be created");
		return new ResponseEntity<ErrorStructure>(errorStructure, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
