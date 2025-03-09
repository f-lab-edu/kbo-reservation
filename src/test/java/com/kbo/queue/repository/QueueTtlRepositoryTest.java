package com.kbo.queue.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueueTtlRepositoryTest {
	@Autowired
	private QueueTtlRepository queueTtlRepository;

	private static final long GAME_ID = 1001L;
	private static final long USER_ID = 2001L;

	@BeforeEach
	void setUp() {
		queueTtlRepository.remove(GAME_ID, USER_ID);
	}

	@Test
	void should_setExpire_when_user() {
		queueTtlRepository.setExpire(GAME_ID, USER_ID, 300);

		boolean expired = queueTtlRepository.isExpired(GAME_ID, USER_ID);

		assertThat(expired).isFalse();
	}

	@Test
	void should_isExpired_when_timeOut() throws InterruptedException {
		queueTtlRepository.setExpire(GAME_ID, USER_ID, 3);
		Thread.sleep(4000);

		boolean expired = queueTtlRepository.isExpired(GAME_ID, USER_ID);

		assertThat(expired).isTrue();
	}
}
