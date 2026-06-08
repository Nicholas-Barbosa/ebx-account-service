package com.ebanx.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ebanx.account.exception.AccountNotFoundException;
import com.ebanx.account.exception.InsufficientFundsException;
import com.ebanx.account.model.Account;
import com.ebanx.account.repository.AccountRepository;

@SpringBootTest
class AccountServiceImplTest {

	@Autowired
	private AccountService service;

	@Autowired
	private AccountRepository repository;

	@BeforeEach
	void setup() {
		repository.removeAll();

		repository.persist(new Account("100", new BigDecimal("100")));
		repository.persist(new Account("200", new BigDecimal("50")));
	}

	@Test
	void shouldDepositSuccessfully() {
		Account account = service.deposit("100", new BigDecimal("50"));

		assertEquals(new BigDecimal("150"), account.getBalance());
	}

	@Test
	void shouldWithdrawSuccessfully() {
		Account account = service.withdraw("100", new BigDecimal("40"));

		assertEquals(new BigDecimal("60"), account.getBalance());
	}

	@Test
	void shouldTransferSuccessfully() {
		List<Account> accounts = service.transfer("100", "200", new BigDecimal("30"));

		Account origin = accounts.get(0);
		Account destination = accounts.get(1);

		assertEquals(new BigDecimal("70"), origin.getBalance());

		assertEquals(new BigDecimal("80"), destination.getBalance());
	}

	@Test
	void shouldThrowWhenInsufficientFunds() {
		assertThrows(InsufficientFundsException.class, () -> service.withdraw("100", new BigDecimal("1000")));
	}

	@Test
	void shouldThrowWhenDestinationAccountDoesNotExist() {
		assertThrows(AccountNotFoundException.class, () -> service.transfer("100", "999", new BigDecimal("50")));
	}

	@Test
	void shouldThrowWhenOriginAccountHasInsufficientFunds() {
		assertThrows(InsufficientFundsException.class, () -> service.transfer("100", "200", new BigDecimal("1000")));
	}
}
