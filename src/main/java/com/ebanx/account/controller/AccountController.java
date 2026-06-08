package com.ebanx.account.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebanx.account.service.AccountService;
import com.ebanx.account.service.EventDispatcher;
import com.ebanx.account.service.strategy.dto.EventRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;
	private final EventDispatcher eventDispatcher;

	@PostMapping("/event")
	public ResponseEntity<?> postEvent(@RequestBody EventRequest req) {
		Map<String, Object> response = eventDispatcher.dispatch(req);
		return ResponseEntity.status(201).body(response);
	}

	@GetMapping("/balance")
	public ResponseEntity<?> getById(@RequestParam("account_id") String account) {
		return accountService.findById(account).map(a -> ResponseEntity.ok(a.getBalance()))
				.orElseGet(() -> ResponseEntity.status(404).body(BigDecimal.ZERO));
	}

	@PostMapping("/reset")
	public ResponseEntity<?> reset() {
		accountService.resetAll();
		return ResponseEntity.ok("OK");
	}
}
