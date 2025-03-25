package com.kbo.sse.publisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import com.kbo.sse.event.SseEvent;

@ExtendWith(MockitoExtension.class)
class SseEventPublisherTest {
	private static final long GAME_ID = 1001L;
	private static final String SSE_CHANNEL = "sse-events";

	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	private SseEventPublisher sseEventPublisher;

	@BeforeEach
	void setUp() {
		sseEventPublisher = new SseEventPublisher(redisTemplate);
	}

	@Test
	void should_publish_when_valid() {
		sseEventPublisher.publish(GAME_ID);

		verify(redisTemplate, times(1))
			.convertAndSend(eq(SSE_CHANNEL), argThat(arg -> {
				if (!(arg instanceof SseEvent sseEvent)) {
					return false;
				}
				return sseEvent.gameId() == GAME_ID;
			}));
	}

	@Test
	void should_throwException_when_invalid() {
		sseEventPublisher.publish(999L);

		assertThrows(AssertionError.class, () -> verify(redisTemplate, times(1))
			.convertAndSend(eq(SSE_CHANNEL), argThat(arg -> {
				if (!(arg instanceof SseEvent sseEvent)) {
					return false;
				}
				return sseEvent.gameId() == GAME_ID;
			})));
	}

	@Test
	void should_throwException_when_invalidChannel() {
		sseEventPublisher.publish(GAME_ID);

		assertThrows(AssertionError.class, () -> verify(redisTemplate, times(1))
			.convertAndSend(eq("invalid-channel"), any()));
	}
}
