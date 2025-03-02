package com.kbo.config;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisConnectionChecker {
	private final RedisConnectionFactory redisConnectionFactory;

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
