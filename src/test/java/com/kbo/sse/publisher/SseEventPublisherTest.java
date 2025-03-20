package com.kbo.sse.publisher;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbo.sse.event.SseEvent;

@ExtendWith(MockitoExtension.class)
class SseEventPublisherTest {
	@Mock
	private RedisTemplate<String, String> redisTemplate;

	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	private SseEventPublisher sseEventPublisher;

	private static final String SSE_CHANNEL = "sse-events";
	private static final long GAME_ID = 1001L;

	@Test
	void should_publish_when_valid() throws JsonProcessingException {
		String message = "{\"gameId\":" + GAME_ID + "}";
		when(objectMapper.writeValueAsString(new SseEvent(GAME_ID))).thenReturn(message);

		sseEventPublisher.publish(GAME_ID);

		verify(redisTemplate, times(1)).convertAndSend(SSE_CHANNEL, message);
	}

	@Test
	void should_throwException_when_serializationFailed() throws JsonProcessingException {
		when(objectMapper.writeValueAsString(new SseEvent(GAME_ID))).thenThrow(JsonProcessingException.class);

		sseEventPublisher.publish(GAME_ID);

		verify(redisTemplate, never()).convertAndSend(SSE_CHANNEL, GAME_ID);
	}
}
