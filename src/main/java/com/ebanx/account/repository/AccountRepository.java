package com.ebanx.account.repository;

import java.util.Optional;

import com.ebanx.account.model.Account;

public interface AccountRepository {

	Optional<Account>findById(String id);
	
	void persist(Account account);
	
	void removeAll();
}
