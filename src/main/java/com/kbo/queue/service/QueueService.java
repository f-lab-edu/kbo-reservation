package com.kbo.queue.service;

import org.springframework.stereotype.Service;

import com.kbo.queue.controller.response.QueueResponse;
import com.kbo.queue.controller.response.QueueSession;
import com.kbo.queue.repository.QueueRepository;
import com.kbo.queue.repository.QueueSessionRepository;
import com.kbo.sse.publisher.SseEventPublisher;

@Service
public class QueueService {
	private final QueueRepository queueRepository;
	private final QueueSessionRepository queueSessionRepository;
	private SseEventPublisher sseEventPublisher;

	public QueueService(
		QueueRepository queueRepository,
		QueueSessionRepository queueSessionRepository,
		SseEventPublisher sseEventPublisher
	) {
		this.queueRepository = queueRepository;
		this.queueSessionRepository = queueSessionRepository;
		this.sseEventPublisher = sseEventPublisher;
	}

	public QueueSession join(long gameId, long userId) {
		if (queueRepository.score(gameId, userId) != null) {
			queueRepository.remove(gameId, userId);
		}

		queueRepository.add(gameId, userId);

		QueueSession queueSession = QueueSession.create(gameId, userId);
		queueSessionRepository.saveSession(queueSession);

		sseEventPublisher.publish(gameId);

		return queueSession;
	}

	public QueueResponse getPosition(long gameId, long userId) {
		Long rank = queueRepository.rank(gameId, userId);
		if (rank == null) {
			throw new IllegalArgumentException("User " + userId + " is not in the queue.");
		}

		long totalQueueSize = queueRepository.size(gameId);
		return new QueueResponse(rank + 1, totalQueueSize, false);
	}

	public QueueSession getSession(String token) {
		return queueSessionRepository.getSession(token);
	}
}
