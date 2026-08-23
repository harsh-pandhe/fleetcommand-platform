package com.fleetcommand.backend.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		return validationError(exception.getBindingResult());
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ApiError> handleBindException(BindException exception) {
		return validationError(exception.getBindingResult());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException exception) {
		Map<String, String> details = new LinkedHashMap<>();
		exception.getConstraintViolations().forEach(violation ->
				details.putIfAbsent(violation.getPropertyPath().toString(), violation.getMessage()));

		return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Validation failed", details);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ApiError> handleNotFound(NoSuchElementException exception) {
		return error(HttpStatus.NOT_FOUND, "NOT_FOUND", "Resource not found", Map.of());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException exception) {
		return error(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "Access denied", Map.of());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleUnhandled(Exception exception) {
		log.error("Unhandled request exception", exception);
		return error(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "An unexpected error occurred", Map.of());
	}

	private ResponseEntity<ApiError> error(HttpStatus status, String code, String message, Map<String, String> details) {
		return ResponseEntity.status(status).body(ApiError.of(code, message, details));
	}

	private ResponseEntity<ApiError> validationError(BindingResult bindingResult) {
		Map<String, String> details = new LinkedHashMap<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			details.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Validation failed", details);
	}
}
