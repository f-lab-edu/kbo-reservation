package com.kbo.queue.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kbo.queue.controller.response.QueueResponse;
import com.kbo.queue.repository.QueueRepository;
import com.kbo.queue.repository.QueueTtlRepository;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {
	@Mock
	private QueueRepository queueRepository;

	@Mock
	private QueueTtlRepository queueTtlRepository;

	@InjectMocks
	private QueueService queueService;

	private static final long GAME_ID = 1001L;
	private static final long USER_ID = 2001L;
	private static final long RANK = 0L;
	private static final long SIZE = 1L;

	@Test
	void should_join_when_user() {
		when(queueRepository.rank(GAME_ID, USER_ID)).thenReturn(RANK);
		when(queueRepository.size(GAME_ID)).thenReturn(SIZE);

		QueueResponse result = queueService.join(GAME_ID, USER_ID);

		assertThat(result).isNotNull();
		assertThat(result.currentRank()).isEqualTo(1L);
		assertThat(result.queueSize()).isEqualTo(SIZE);

		verify(queueRepository, times(1)).add(GAME_ID, USER_ID);
		verify(queueTtlRepository, times(1)).setExpire(GAME_ID, USER_ID, 30);
	}

	@Test
	void should_join_when_existsUser() {
		when(queueRepository.score(GAME_ID, USER_ID)).thenReturn(1.1);
		when(queueRepository.rank(GAME_ID, USER_ID)).thenReturn(RANK);
		when(queueRepository.size(GAME_ID)).thenReturn(SIZE);

		QueueResponse result = queueService.join(GAME_ID, USER_ID);

		assertThat(result).isNotNull();
		assertThat(result.currentRank()).isEqualTo(1L);
		assertThat(result.queueSize()).isEqualTo(SIZE);

		verify(queueRepository, times(1)).remove(GAME_ID, USER_ID);
		verify(queueRepository, times(1)).add(GAME_ID, USER_ID);
		verify(queueTtlRepository, times(1)).setExpire(GAME_ID, USER_ID, 30);
	}
}
