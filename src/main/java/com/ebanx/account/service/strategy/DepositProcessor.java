package com.ebanx.account.service.strategy;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebanx.account.dto.AccountDTO;
import com.ebanx.account.model.Account;
import com.ebanx.account.service.AccountService;
import com.ebanx.account.service.strategy.dto.EventRequest;
import com.ebanx.account.service.strategy.dto.EventRequest.DepositRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class DepositProcessor implements EventProcessor {

	private final AccountService accountService;

	@Override
	public Map<String, Object> handle(EventRequest e) {
		DepositRequest d = (DepositRequest) e;
		Account deposit = accountService.deposit(d.destination(), d.amount());
		return Map.of("destination", AccountDTO.fromDomain(deposit));
	}

	@Override
	public String getSupportedEvent() {
		return "deposit";
	}

}
