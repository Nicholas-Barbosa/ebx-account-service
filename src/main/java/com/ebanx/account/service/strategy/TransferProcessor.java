package com.ebanx.account.service.strategy;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebanx.account.dto.AccountDTO;
import com.ebanx.account.model.Account;
import com.ebanx.account.service.AccountService;
import com.ebanx.account.service.strategy.dto.EventRequest;
import com.ebanx.account.service.strategy.dto.EventRequest.TransferRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class TransferProcessor implements EventProcessor {

	private final AccountService accountService;

	@Override
	public String getSupportedEvent() {
		return "transfer";
	}

	@Override
	public Map<String, Object> handle(EventRequest e) {
		TransferRequest transfer = (TransferRequest) e;
		List<Account> accounts = accountService.transfer(transfer.origin(), transfer.destination(), transfer.amount());
		return Map.of("origin", AccountDTO.fromDomain(accounts.get(0)), "destination",
				AccountDTO.fromDomain(accounts.get(1)));
	}

}
