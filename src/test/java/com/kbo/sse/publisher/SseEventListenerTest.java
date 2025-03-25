package com.kbo.sse.publisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbo.sse.service.SseService;

@ExtendWith(MockitoExtension.class)
class SseEventListenerTest {
	private static final long GAME_ID = 1001L;

	@Mock
	private SseService sseService;

	private SseEventListener sseEventListener;

	@BeforeEach
	void setUp() {
		sseEventListener = new SseEventListener(sseService, new ObjectMapper());
	}

	@Test
	void should_process_when_valid() {
		String payload = "{\"gameId\":" + GAME_ID + "}";
		Message redisMessage = mock(Message.class);
		when(redisMessage.getBody()).thenReturn(payload.getBytes());

		sseEventListener.onMessage(redisMessage, null);

		verify(sseService, times(1)).send(GAME_ID);
	}

	@Test
	void should_throwException_when_parsingFailed() {
		String payload = "{invalid_json}";
		Message redisMessage = mock(Message.class);
		when(redisMessage.getBody()).thenReturn(payload.getBytes());

		assertDoesNotThrow(() -> sseEventListener.onMessage(redisMessage, null));

		verify(sseService, never()).send(GAME_ID);
	}
}
