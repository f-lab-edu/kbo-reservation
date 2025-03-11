package com.kbo.queue.repository;

import static com.kbo.queue.constant.QueueKeyPrefix.*;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QueueTtlRepository {
	private final RedisTemplate<String, Object> redisTemplate;

	public QueueTtlRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setExpire(long gameId, long userId, long ttlSeconds) {
		redisTemplate.opsForValue().set(TTL.format(gameId, userId), userId, ttlSeconds, TimeUnit.SECONDS);
	}

	public boolean isExpired(long gameId, long userId) {
		return redisTemplate.opsForValue().get(TTL.format(gameId, userId)) == null;
	}

	public void remove(long gameId, long userId) {
		redisTemplate.delete(TTL.format(gameId, userId));
	}
}
