package com.kbo.queue.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kbo.queue.controller.response.QueueSession;
import com.kbo.queue.repository.QueueRepository;
import com.kbo.queue.repository.QueueSessionRepository;
import com.kbo.sse.publisher.SseEventPublisher;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {
	@Mock
	private QueueRepository queueRepository;

	@Mock
	private QueueSessionRepository queueSessionRepository;

	@Mock
	private SseEventPublisher sseEventPublisher;

	@InjectMocks
	private QueueService queueService;

	private static final long GAME_ID = 1001L;
	private static final long USER_ID = 2001L;

	@Test
	void should_join_when_user() {
		when(queueRepository.score(GAME_ID, USER_ID)).thenReturn(null);

		doNothing().when(queueSessionRepository).saveSession(any(QueueSession.class), anyLong());

		QueueSession result = queueService.join(GAME_ID, USER_ID);

		assertThat(result).isNotNull();
		assertThat(result.token()).isNotBlank();
		assertThat(result.gameId()).isEqualTo(GAME_ID);
		assertThat(result.userId()).isEqualTo(USER_ID);

		verify(queueRepository, times(1)).add(GAME_ID, USER_ID);
		verify(queueSessionRepository, times(1)).saveSession(any(QueueSession.class), anyLong());
		verify(sseEventPublisher, times(1)).publish(GAME_ID);
	}

	@Test
	void should_join_when_existsUser() {
		when(queueRepository.score(GAME_ID, USER_ID)).thenReturn(1.1);

		doNothing().when(queueSessionRepository).saveSession(any(QueueSession.class), anyLong());

		QueueSession result = queueService.join(GAME_ID, USER_ID);

		assertThat(result).isNotNull();
		assertThat(result.token()).isNotBlank();
		assertThat(result.gameId()).isEqualTo(GAME_ID);
		assertThat(result.userId()).isEqualTo(USER_ID);

		verify(queueRepository, times(1)).remove(GAME_ID, USER_ID);
		verify(queueRepository, times(1)).add(GAME_ID, USER_ID);
		verify(queueSessionRepository, times(1)).saveSession(any(QueueSession.class), anyLong());
		verify(sseEventPublisher, times(1)).publish(GAME_ID);
	}
}
