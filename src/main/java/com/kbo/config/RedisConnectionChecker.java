package com.kbo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class RedisConnectionChecker {
	private static final Logger log = LoggerFactory.getLogger(RedisConnectionChecker.class);
	private final RedisConnectionFactory redisConnectionFactory;

	public RedisConnectionChecker(RedisConnectionFactory redisConnectionFactory) {
		this.redisConnectionFactory = redisConnectionFactory;
	}

	@PostConstruct
	public void checkRedisConnection() {
		try {
			String response = redisConnectionFactory.getConnection().ping();
			log.info("Redis is connected successfully! Response: {}", response);
		} catch (Exception e) {
			log.error("Redis connection failed: {}", e.getMessage());
		}
	}
}
