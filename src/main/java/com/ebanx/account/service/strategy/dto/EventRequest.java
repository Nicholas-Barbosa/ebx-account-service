package com.ebanx.account.service.strategy.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes({ @JsonSubTypes.Type(value = EventRequest.DepositRequest.class, name = "deposit"),
		@JsonSubTypes.Type(value = EventRequest.WithdrawRequest.class, name = "withdraw"),
		@JsonSubTypes.Type(value = EventRequest.TransferRequest.class, name = "transfer") })
public sealed interface EventRequest
		permits EventRequest.DepositRequest, EventRequest.WithdrawRequest, EventRequest.TransferRequest {

	String type();

	public record DepositRequest(String destination, BigDecimal amount) implements EventRequest {

		@Override
		public String type() {
			// TODO Auto-generated method stub
			return "deposit";
		}

	}

	public record WithdrawRequest(String origin, BigDecimal amount) implements EventRequest {

		@Override
		public String type() {
			// TODO Auto-generated method stub
			return "withdraw";
		}

	}

	public record TransferRequest(String origin, BigDecimal amount, String destination) implements EventRequest {

		@Override
		public String type() {
			return "transfer";
		}

	}
}
