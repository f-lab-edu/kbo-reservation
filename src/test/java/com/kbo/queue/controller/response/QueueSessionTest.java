package com.kbo.queue.controller.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class QueueSessionTest {
	private static final Logger log = LoggerFactory.getLogger(QueueSessionTest.class);

	private static final long GAME_ID = 1001L;
	private static final long USER_ID = 2001L;

	@Test
	void 대기열_토큰_생성_성공() {
		QueueSession queueSession = QueueSession.create(GAME_ID, USER_ID);

		log.info("queueSession token: {}", queueSession.token());

		assertThat(queueSession).isNotNull();
		assertThat(queueSession.gameId()).isEqualTo(GAME_ID);
		assertThat(queueSession.userId()).isEqualTo(USER_ID);
	}
}
