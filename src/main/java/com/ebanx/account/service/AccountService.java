package com.ebanx.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.ebanx.account.model.Account;

public interface AccountService {

	Account withdraw(String origin, BigDecimal amount);

	Account deposit(String origin, BigDecimal amount);

	List<Account> transfer(String origin, String destination, BigDecimal amount);

	void resetAll();

	Optional<Account> findById(String id);
}
