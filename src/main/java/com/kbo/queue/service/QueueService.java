package com.kbo.queue.service;

import org.springframework.stereotype.Service;

import com.kbo.queue.controller.response.QueueResponse;
import com.kbo.queue.repository.QueueRepository;
import com.kbo.queue.repository.QueueTtlRepository;

@Service
public class QueueService {
	private static final long QUEUE_TTL_TIME_SECONDS = 30;

	private final QueueRepository queueRepository;
	private final QueueTtlRepository queueTtlRepository;

	public QueueService(QueueRepository queueRepository, QueueTtlRepository queueTtlRepository) {
		this.queueRepository = queueRepository;
		this.queueTtlRepository = queueTtlRepository;
	}

	public QueueResponse join(long gameId, long userId) {
		if (queueRepository.score(gameId, userId) != null) {
			queueRepository.remove(gameId, userId);
		}

		queueRepository.add(gameId, userId);
		queueTtlRepository.setExpire(gameId, userId, QUEUE_TTL_TIME_SECONDS);
		return getPosition(gameId, userId);
	}

	public QueueResponse getPosition(long gameId, long userId) {
		Long rank = queueRepository.rank(gameId, userId);
		if (rank == null) {
			throw new IllegalArgumentException("User " + userId + " is not in the queue.");
		}

		long totalQueueSize = queueRepository.size(gameId);
		return new QueueResponse(rank + 1, totalQueueSize, false);
	}
}
