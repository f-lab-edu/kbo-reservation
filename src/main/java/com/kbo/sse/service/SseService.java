package com.kbo.sse.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kbo.queue.controller.response.QueueResponse;
import com.kbo.queue.controller.response.QueueSession;
import com.kbo.queue.service.QueueService;

@Service
public class SseService {
	private static final Logger log = LoggerFactory.getLogger(SseService.class);

	private final QueueService queueService;
	private final SubscriptionManager subscriptionManager;

	public SseService(QueueService queueService, SubscriptionManager subscriptionManager) {
		this.queueService = queueService;
		this.subscriptionManager = subscriptionManager;
	}

	public SseEmitter add(String token) {
		QueueSession session = queueService.getSession(token);
		long gameId = session.gameId();
		long userId = session.userId();

		SseEmitter sseEmitter = new SseEmitter();
		subscriptionManager.add(gameId, userId, sseEmitter);

		sseEmitter.onCompletion(() -> remove(gameId, userId));
		sseEmitter.onTimeout(() -> {
			try {
				sseEmitter.send(SseEmitter.event().name("timeout").data("Connection timed out."));
			} catch (IOException e) {
				log.error("SseEmitter event error gameId: {}", gameId, e);
			} finally {
				remove(gameId, userId);
			}
		});

		return sseEmitter;
	}

	public void send(long gameId) {
		subscriptionManager.getSubscribers(gameId)
			.forEach((userId, sseEmitter) -> sendUser(gameId, userId));
	}

	private void sendUser(long gameId, long userId) {
		SseEmitter sseEmitter = subscriptionManager.getSubscribers(gameId).get(userId);
		if (sseEmitter != null) {
			try {
				QueueResponse response = queueService.getPosition(gameId, userId);
				sseEmitter.send(response);
			} catch (IOException e) {
				sseEmitter.completeWithError(e);
				remove(gameId, userId);
			}
		}
	}

	private void remove(long gameId, long userId) {
		subscriptionManager.remove(gameId, userId);
	}
}
