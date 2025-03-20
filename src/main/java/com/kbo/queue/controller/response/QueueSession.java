package com.kbo.queue.controller.response;

import java.util.UUID;

public record QueueSession(
	String token,
	long gameId,
	long userId
) {
	public static QueueSession create(long gameId, long userId) {
		return new QueueSession(UUID.randomUUID().toString(), gameId, userId);
	}
}
