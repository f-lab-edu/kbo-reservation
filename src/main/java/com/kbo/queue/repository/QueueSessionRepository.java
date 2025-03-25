package com.kbo.queue.repository;

import static com.kbo.queue.constant.QueueKeyPrefix.*;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.kbo.queue.controller.response.QueueSession;

@Repository
public class QueueSessionRepository {
	private final RedisTemplate<String, Object> redisTemplate;

	public QueueSessionRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void saveSession(QueueSession queueSession, long timeout) {
		String key = formatKey(queueSession.token());
		redisTemplate.opsForValue().set(key, queueSession, timeout, TimeUnit.SECONDS);
	}

	public QueueSession getSession(String token) {
		return (QueueSession)redisTemplate.opsForValue().get(formatKey(token));
	}

	public boolean isSessionExpired(String token) {
		return redisTemplate.opsForValue().get(formatKey(token)) == null;
	}

	public void removeSession(String token) {
		redisTemplate.delete(formatKey(token));
	}

	private String formatKey(String token) {
		return SESSION.format(token);
	}
}
