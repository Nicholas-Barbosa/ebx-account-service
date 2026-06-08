package com.ebanx.account.service.strategy;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebanx.account.dto.AccountDTO;
import com.ebanx.account.model.Account;
import com.ebanx.account.service.AccountService;
import com.ebanx.account.service.strategy.dto.EventRequest;
import com.ebanx.account.service.strategy.dto.EventRequest.WithdrawRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class WithdrawProcessor implements EventProcessor {

	private final AccountService accountService;

	@Override
	public String getSupportedEvent() {
		return "withdraw";
	}

	@Override
	public Map<String, Object> handle(EventRequest e) {
		WithdrawRequest req = (WithdrawRequest) e;
		Account withdraw = accountService.withdraw(req.origin(), req.amount());
		return Map.of("origin", AccountDTO.fromDomain(withdraw));
	}

}
