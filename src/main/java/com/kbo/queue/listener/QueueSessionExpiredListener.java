package com.kbo.queue.listener;

import static com.kbo.util.ValidatorUtil.*;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.kbo.queue.repository.QueueRepository;

@Component
public class QueueSessionExpiredListener extends KeyExpirationEventMessageListener {
	private static final Logger log = LoggerFactory.getLogger(QueueSessionExpiredListener.class);
	private final QueueRepository queueRepository;

	private static final String SESSION_PREFIX = "queue:session:";

	public QueueSessionExpiredListener(
		@Qualifier("keyExpirationConfig") RedisMessageListenerContainer listenerContainer,
		QueueRepository queueRepository
	) {
		super(listenerContainer);
		this.queueRepository = queueRepository;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
		log.info("Queue session expired: {}", expiredKey);

		if (!expiredKey.startsWith(SESSION_PREFIX)) {
			return;
		}

		String token = expiredKey.substring(SESSION_PREFIX.length());

		if (!isValidToken(token)) {
			log.error("Invalid session token: {}", token);
			return;
		}

		String[] tokenParts = token.split("-");
		long gameId = Long.parseLong(tokenParts[1]);
		long userId = Long.parseLong(tokenParts[2]);

		queueRepository.remove(gameId, userId);
	}

	private boolean isValidToken(String token) {
		String[] parts = token.split("-");

		if (parts.length != 4) {
			return false;
		}

		String randomString = parts[0];
		String gameIdString = parts[1];
		String userIdString = parts[2];
		String nanoTimeString = parts[3];

		if (!isAlphanumeric8(randomString)) {
			return false;
		}

		try {
			Long.parseLong(gameIdString);
			Long.parseLong(userIdString);
			Long.parseLong(nanoTimeString);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}
