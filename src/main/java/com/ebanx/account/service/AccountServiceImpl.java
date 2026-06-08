package com.ebanx.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ebanx.account.exception.AccountNotFoundException;
import com.ebanx.account.exception.InsufficientFundsException;
import com.ebanx.account.model.Account;
import com.ebanx.account.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	@Override
	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}

	@Override
	public Account withdraw(String origin, BigDecimal amount) {
		Account account = this.accountRepository.findById(origin)
				.orElseThrow(() -> new AccountNotFoundException(origin));
		synchronized (account) {
			if (account.getBalance().compareTo(amount) < 0)
				throw new InsufficientFundsException(origin);
			account.setBalance(account.getBalance().subtract(amount));
			return account;
		}

	}

	@Override
	public Account deposit(String origin, BigDecimal amount) {
		Account account = accountRepository.findById(origin).orElseGet(() -> {
			Account newAccount = new Account(origin, BigDecimal.ZERO);
			this.accountRepository.persist(newAccount);
			return newAccount;
		});
		synchronized (account) {
			account.setBalance(account.getBalance().add(amount));
			return account;
		}

	}

	@Override
	public List<Account> transfer(String origin, String destination, BigDecimal amount) {
		if (origin.equalsIgnoreCase(destination))
			throw new IllegalArgumentException("Contas iguais");
		Account originAccount = this.findById(origin).orElseThrow(() -> new AccountNotFoundException(origin));
		synchronized (originAccount) {
			withdraw(origin, amount);
			try {
				Account destAccount = deposit(destination, amount);
				return List.of(originAccount, destAccount);
			} catch (RuntimeException e) {
				deposit(origin, amount);
				throw e;
			}

		}

	}

	@Override
	public void resetAll() {
		this.accountRepository.removeAll();

	}

}
