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
	void 유효한_게임ID일_경우_이벤트_발행_성공() {
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
	void 유효하지_않은_게임ID일_경우_예외발생() {
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
	void 유효하지_않은_채널일_경우_예외발생() {
		sseEventPublisher.publish(GAME_ID);

		assertThrows(AssertionError.class, () -> verify(redisTemplate, times(1))
			.convertAndSend(eq("invalid-channel"), any()));
	}
}
