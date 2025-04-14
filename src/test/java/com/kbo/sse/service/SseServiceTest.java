package com.kbo.sse.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kbo.queue.controller.response.QueueResponse;
import com.kbo.queue.controller.response.QueueSession;
import com.kbo.queue.service.QueueService;

@ExtendWith(MockitoExtension.class)
class SseServiceTest {
	@Mock
	private QueueService queueService;

	@Mock
	private SubscriptionManager subscriptionManager;

	@InjectMocks
	private SseService sseService;

	private static final long GAME_ID = 1001L;
	private static final long USER_ID = 2001L;
	private static final String SESSION_TOKEN = "6f9a2b88-3a4c-41d1-99a5-8a720b1c9183";

	@Test
	void 유효한_세션_토큰일_경우_구독_추가_성공() {
		QueueSession queueSession = QueueSession.create(GAME_ID, USER_ID);
		when(queueService.getSession(SESSION_TOKEN)).thenReturn(queueSession);

		SseEmitter emitter = sseService.add(SESSION_TOKEN);

		assertThat(emitter).isNotNull();
		verify(subscriptionManager, times(1)).add(GAME_ID, USER_ID, emitter);
	}

	@Test
	void 유효한_게임ID일_경우_대기열_정보_전송_성공() throws IOException {
		SseEmitter emitter = mock(SseEmitter.class);
		when(subscriptionManager.getSubscribers(GAME_ID)).thenReturn(Map.of(USER_ID, emitter));
		when(queueService.getPosition(GAME_ID, USER_ID)).thenReturn(new QueueResponse(1, 10, false));

		sseService.send(GAME_ID);

		verify(emitter, times(1)).send(any(QueueResponse.class));
	}

	@Test
	void 전송중_예외가_발생할_경우_구독_제거_성공() throws IOException {
		SseEmitter emitter = mock(SseEmitter.class);
		when(subscriptionManager.getSubscribers(GAME_ID)).thenReturn(Map.of(USER_ID, emitter));
		doThrow(IOException.class).when(emitter).send(nullable(Object.class));

		sseService.send(GAME_ID);

		verify(emitter, times(1)).completeWithError(any(IOException.class));
		verify(subscriptionManager, times(1)).remove(GAME_ID, USER_ID);
	}
}
