package com.kbo.queue.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kbo.queue.controller.response.QueueSession;

@SpringBootTest
class QueueSessionRepositoryTest {
	@Autowired
	private QueueSessionRepository queueSessionRepository;

	private static final long GAME_ID = 1001L;
	private static final long USER_ID = 2001L;
	private QueueSession queueSession;

	@BeforeEach
	void setUp() {
		queueSession = QueueSession.create(USER_ID, GAME_ID);
		queueSessionRepository.removeSession(queueSession.token());
	}

	@Test
	void should_saveSession_when_user() {
		queueSessionRepository.saveSession(queueSession);

		QueueSession session = queueSessionRepository.getSession(queueSession.token());

		assertThat(session).isNotNull();
		assertThat(session.token()).isEqualTo(queueSession.token());
		assertThat(session.userId()).isEqualTo(queueSession.userId());
		assertThat(session.gameId()).isEqualTo(queueSession.gameId());
	}

	@Test
	void should_isSessionExpired_when_success() throws InterruptedException {
		queueSessionRepository.saveSession(queueSession);

		Thread.sleep(2000);
		boolean expiredBeforeTTL = queueSessionRepository.isSessionExpired(queueSession.token());
		assertThat(expiredBeforeTTL).isFalse();

		Thread.sleep(29000);
		boolean expiredAfterTTL = queueSessionRepository.isSessionExpired(queueSession.token());
		assertThat(expiredAfterTTL).isTrue();
	}
}
