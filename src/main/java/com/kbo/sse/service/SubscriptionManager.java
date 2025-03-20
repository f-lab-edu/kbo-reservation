package com.kbo.sse.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SubscriptionManager {
	private final Map<Long, Map<Long, SseEmitter>> localSubscribers = new ConcurrentHashMap<>();

	public void add(long gameId, long userId, SseEmitter sseEmitter) {
		localSubscribers.computeIfAbsent(gameId, k -> new ConcurrentHashMap<>()).put(userId, sseEmitter);
	}

	public Map<Long, SseEmitter> getSubscribers(long gameId) {
		return localSubscribers.getOrDefault(gameId, Map.of());
	}

	public void remove(long gameId, long userId) {
		localSubscribers.getOrDefault(gameId, Map.of()).remove(userId);
	}
}
