package com.ebanx.account.exception;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends RuntimeException {

	private final String conta;

	public InsufficientFundsException(String conta) {
		this.conta = conta;
	}
}
