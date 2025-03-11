package com.kbo.queue.controller.response;

public record QueueResponse(
	long currentRank,
	long queueSize,
	boolean isAccepted
) {
}
