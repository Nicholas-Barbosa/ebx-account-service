package com.ebanx.account.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ebanx.account.service.strategy.EventProcessor;
import com.ebanx.account.service.strategy.dto.EventRequest;

@Service
public class EventDispatcher {

	private final Map<String, EventProcessor> eventHandlers;

	public EventDispatcher(List<EventProcessor> eventProcessors) {
		this.eventHandlers = eventProcessors.stream()
				.collect(Collectors.toConcurrentMap(k -> k.getSupportedEvent(), v -> v));
	}

	public Map<String,Object> dispatch(EventRequest req) {
		EventProcessor handler = this.eventHandlers.get(req.type());
		return handler.handle(req);
	}
}
