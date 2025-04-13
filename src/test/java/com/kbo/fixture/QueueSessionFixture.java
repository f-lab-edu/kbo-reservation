package com.kbo.fixture;

import com.kbo.queue.controller.response.QueueSession;

public class QueueSessionFixture {
	public static final long GAME_ID = 1001L;
	public static final long USER_ID = 2001L;

	public static QueueSession get(String token) {
		return new QueueSession(token, GAME_ID, USER_ID);
	}
}
