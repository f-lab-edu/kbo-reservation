package com.kbo.queue.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueueRepositoryTest {
	@Autowired
	private QueueRepository queueRepository;

	private static final long GAME_ID = 1001L;
	private static final long USER_ID_1 = 2001L;
	private static final long USER_ID_2 = 2002L;

	@BeforeEach
	void setUp() {
		queueRepository.remove(GAME_ID, USER_ID_1);
		queueRepository.remove(GAME_ID, USER_ID_2);
	}

	@Test
	void 단건_사용자_대기열_추가_성공() {
		queueRepository.add(GAME_ID, USER_ID_1);

		Long rank = queueRepository.rank(GAME_ID, USER_ID_1);
		assertThat(rank).isNotNull();
		assertThat(rank).isEqualTo(0);
	}

	@Test
	void 다건_사용자_대기열_추가_성공() {
		queueRepository.add(GAME_ID, USER_ID_1);
		queueRepository.add(GAME_ID, USER_ID_2);

		Long size = queueRepository.size(GAME_ID);
		assertThat(size).isEqualTo(2);
	}

	@Test
	void 단건_사용자_대기열_추가후_스코어_조회_성공() {
		queueRepository.add(GAME_ID, USER_ID_1);

		Double score = queueRepository.score(GAME_ID, USER_ID_1);
		assertThat(score).isNotNull();
	}
}
