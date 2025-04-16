package com.kbo.sse.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

class SubscriptionManagerTest {
	private SubscriptionManager subscriptionManager;

	private static final long GAME_ID = 1001L;
	private static final long USER_ID = 2001L;

	@BeforeEach
	void setUp() {
		subscriptionManager = new SubscriptionManager();
	}

	@Test
	void 유효한_요청일_경우_구독자_추가_성공() {
		SseEmitter emitter = new SseEmitter();

		subscriptionManager.add(GAME_ID, USER_ID, emitter);

		Map<Long, SseEmitter> subscribers = subscriptionManager.getSubscribers(GAME_ID);
		assertThat(subscribers).containsKey(USER_ID);
	}

	@Test
	void 유효한_요청일_경우_구독자_제거_성공() {
		SseEmitter emitter = new SseEmitter();
		subscriptionManager.add(GAME_ID, USER_ID, emitter);

		subscriptionManager.remove(GAME_ID, USER_ID);

		Map<Long, SseEmitter> subscribers = subscriptionManager.getSubscribers(GAME_ID);
		assertThat(subscribers).doesNotContainKey(USER_ID);
	}
}
