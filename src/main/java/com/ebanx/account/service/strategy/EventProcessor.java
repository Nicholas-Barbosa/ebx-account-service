package com.ebanx.account.service.strategy;

import java.util.Map;

import com.ebanx.account.service.strategy.dto.EventRequest;

public sealed interface EventProcessor permits DepositProcessor, WithdrawProcessor, TransferProcessor {

	String getSupportedEvent();
	
	Map<String,Object> handle(EventRequest e);
}
