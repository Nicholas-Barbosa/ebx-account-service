package com.ebanx.account.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ebanx.account.dto.ErrorResponse;
import com.ebanx.account.exception.AccountNotFoundException;
import com.ebanx.account.exception.InsufficientFundsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<?> handleAccountNotFound(AccountNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
	} 

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<ErrorResponse> handleNotBalance(InsufficientFundsException e) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(new ErrorResponse("Saldo insuficiente"));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
	}
}
