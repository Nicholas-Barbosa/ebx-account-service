package com.ebanx.account.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.ebanx.account.model.Account;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public Optional<Account> findById(String id) {
		return Optional.ofNullable(accounts.get(id));
	}

	@Override
	public void persist(Account account) {
		this.accounts.put(account.getId(), account);

	}

	@Override
	public void removeAll() {
		this.accounts.clear();

	}

}
