package com.kbo.config;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class RedisConnectionTest {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	void 레디스_연결_성공() {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set("testKey", "testValue");

		String value = valueOperations.get("testKey");

		assertThat(value).isEqualTo("testValue");
	}
}
