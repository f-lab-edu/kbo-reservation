package com.kbo.sse.publisher;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbo.sse.event.SseEvent;
import com.kbo.sse.service.SseService;

@ExtendWith(MockitoExtension.class)
class SseEventListenerTest {
	@Mock
	private SseService sseService;

	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	private SseEventListener sseEventListener;

	private static final long GAME_ID = 1001L;

	@Test
	void should_process_when_valid() throws JsonProcessingException {
		SseEvent sseEvent = new SseEvent(GAME_ID);
		String payload = "{\"gameId\":" + GAME_ID + "}";

		Message redisMessage = mock(Message.class);
		when(redisMessage.getBody()).thenReturn(payload.getBytes());
		when(objectMapper.readValue(payload, SseEvent.class)).thenReturn(sseEvent);

		sseEventListener.onMessage(redisMessage, null);

		verify(sseService, times(1)).send(GAME_ID);
	}

	@Test
	void should_throwException_when_parsingFailed() throws JsonProcessingException {
		String payload = "{invalid_json}";
		Message redisMessage = mock(Message.class);
		when(redisMessage.getBody()).thenReturn(payload.getBytes());
		when(objectMapper.readValue(payload, SseEvent.class)).thenThrow(JsonProcessingException.class);

		sseEventListener.onMessage(redisMessage, null);

		verify(sseService, never()).send(GAME_ID);
	}
}
