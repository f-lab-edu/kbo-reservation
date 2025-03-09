package com.kbo.queue.repository;

import static com.kbo.queue.constant.QueueKeyPrefix.*;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QueueRepository {
	private final RedisTemplate<String, Object> redisTemplate;

	public QueueRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void add(long gameId, long userId) {
		redisTemplate.opsForZSet().add(QUEUE.format(gameId), userId, System.nanoTime());
	}

	public Long rank(long gameId, long userId) {
		return redisTemplate.opsForZSet().rank(QUEUE.format(gameId), userId);
	}

	public Double score(long gameId, long userId) {
		return redisTemplate.opsForZSet().score(QUEUE.format(gameId), userId);
	}

	public Long size(long gameId) {
		return redisTemplate.opsForZSet().size(QUEUE.format(gameId));
	}

	public void remove(long gameId, long userId) {
		redisTemplate.opsForZSet().remove(QUEUE.format(gameId), userId);
	}
}
