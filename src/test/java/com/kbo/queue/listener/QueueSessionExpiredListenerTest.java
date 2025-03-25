package com.kbo.queue.listener;

import static org.mockito.Mockito.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.kbo.queue.repository.QueueRepository;

@ExtendWith(MockitoExtension.class)
class QueueSessionExpiredListenerTest {
	@Mock
	private QueueRepository queueRepository;

	@Mock
	private RedisMessageListenerContainer container;

	private QueueSessionExpiredListener listener;

	@BeforeEach
	void setUp() {
		listener = new QueueSessionExpiredListener(container, queueRepository);
	}

	@Test
	void should_prefix_when_invalid() {
		String key = "invalid:session:abcD1234-1001-2001-123456789";
		Message message = mock(Message.class);
		when(message.getBody()).thenReturn(key.getBytes(StandardCharsets.UTF_8));

		listener.onMessage(message, null);

		verifyNoInteractions(queueRepository);
	}

	@Test
	void should_token_when_notExist() {
		String key = "queue:session:";
		Message message = mock(Message.class);
		when(message.getBody()).thenReturn(key.getBytes(StandardCharsets.UTF_8));

		listener.onMessage(message, null);

		verifyNoInteractions(queueRepository);
	}

	@Test
	void should_token_when_isShort() {
		String key = "queue:session:abcD1234-10";
		Message message = mock(Message.class);
		when(message.getBody()).thenReturn(key.getBytes(StandardCharsets.UTF_8));

		listener.onMessage(message, null);

		verifyNoInteractions(queueRepository);
	}

	@Test
	void should_gameId_when_invalidNumberFormat() {
		String key = "queue:session:abcD1234-xyz-20-123456789";
		Message message = mock(Message.class);
		when(message.getBody()).thenReturn(key.getBytes(StandardCharsets.UTF_8));

		listener.onMessage(message, null);

		verifyNoInteractions(queueRepository);
	}

	@Test
	void should_randomString_when_invalid() {
		String key = "queue:session:abc*1234-1001-2001-123456789";
		Message message = mock(Message.class);
		when(message.getBody()).thenReturn(key.getBytes(StandardCharsets.UTF_8));

		listener.onMessage(message, null);

		verifyNoInteractions(queueRepository);
	}

	@Test
	void should_remove_when_valid() {
		String key = "queue:session:abcD1234-1001-2001-123456789";
		Message message = mock(Message.class);
		when(message.getBody()).thenReturn(key.getBytes(StandardCharsets.UTF_8));

		listener.onMessage(message, null);

		verify(queueRepository, times(1)).remove(1001L, 2001L);
	}
}
