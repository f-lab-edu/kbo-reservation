package com.kbo.sse.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbo.sse.event.SseEvent;
import com.kbo.sse.service.SseService;

@Component
public class SseEventListener implements MessageListener {
	private static final Logger log = LoggerFactory.getLogger(SseEventListener.class);

	private final SseService sseService;
	private final ObjectMapper objectMapper;

	public SseEventListener(SseService sseService, ObjectMapper objectMapper) {
		this.sseService = sseService;
		this.objectMapper = objectMapper;
	}

	@Override
	public void onMessage(@NonNull Message message, byte[] pattern) {
		String payload = new String(message.getBody());
		try {
			SseEvent sseEvent = objectMapper.readValue(payload, SseEvent.class);
			sseService.send(sseEvent.gameId());
		} catch (JsonProcessingException e) {
			log.error("Failed deserialize redis message: {}", payload, e);
		}
	}
}
