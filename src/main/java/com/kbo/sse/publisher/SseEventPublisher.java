package com.kbo.sse.publisher;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kbo.sse.event.SseEvent;

@Component
public class SseEventPublisher {
	private static final String SSE_CHANNEL = "sse-events";

	private final RedisTemplate<String, Object> redisTemplate;

	public SseEventPublisher(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void publish(long gameId) {
		redisTemplate.convertAndSend(SSE_CHANNEL, new SseEvent(gameId));
	}
}
