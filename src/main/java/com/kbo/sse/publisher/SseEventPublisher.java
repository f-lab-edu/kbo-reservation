package com.kbo.sse.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbo.sse.event.SseEvent;

@Component
public class SseEventPublisher {
	private static final Logger log = LoggerFactory.getLogger(SseEventPublisher.class);

	private final RedisTemplate<String, String> stringRedisTemplate;
	private final ObjectMapper objectMapper;

	private static final String SSE_CHANNEL = "sse-events";

	public SseEventPublisher(RedisTemplate<String, String> stringRedisTemplate, ObjectMapper objectMapper) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.objectMapper = objectMapper;
	}

	public void publish(long gameId) {
		try {
			String message = objectMapper.writeValueAsString(new SseEvent(gameId));
			stringRedisTemplate.convertAndSend(SSE_CHANNEL, message);
		} catch (JsonProcessingException e) {
			log.error("JSON Serialization error gameId: {}", gameId, e);
		} catch (Exception e) {
			log.error("Redis Publisher error gameId: {}", gameId, e);
		}
	}
}
