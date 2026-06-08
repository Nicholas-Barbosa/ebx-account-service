package com.ebanx.account.dto;

import java.math.BigDecimal;

import com.ebanx.account.model.Account;

import lombok.Builder;

@Builder
public record AccountDTO(String id, BigDecimal balance) {

	public static AccountDTO fromDomain(Account account) {
		return new AccountDTO(account.getId(), account.getBalance());
	}
}
