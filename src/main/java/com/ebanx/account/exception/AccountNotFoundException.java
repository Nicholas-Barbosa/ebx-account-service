package com.ebanx.account.exception;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException {

	private final String conta;

	public AccountNotFoundException(String conta) {
		this.conta = conta;
	}
}
