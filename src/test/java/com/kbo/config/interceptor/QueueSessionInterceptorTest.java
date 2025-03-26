package com.kbo.config.interceptor;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.kbo.fixture.QueueSessionFixture;
import com.kbo.queue.repository.QueueSessionRepository;

import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class QueueSessionInterceptorTest {
	@Mock
	private QueueSessionRepository queueSessionRepository;

	private QueueSessionInterceptor queueSessionInterceptor;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private static final String SESSION_TOKEN = "session-token";

	@BeforeEach
	void setUp() {
		queueSessionInterceptor = new QueueSessionInterceptor(queueSessionRepository);
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	void should_token_when_missing() throws Exception {
		boolean result = queueSessionInterceptor.preHandle(request, response, new Object());

		assertThat(result).isFalse();
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
		assertThat(response.getContentAsString()).contains("Missing Session-Token");
	}

	@Test
	void should_token_when_invalid() throws Exception {
		request.addHeader("X-Session-Token", SESSION_TOKEN);
		when(queueSessionRepository.getSession(SESSION_TOKEN)).thenReturn(null);

		boolean result = queueSessionInterceptor.preHandle(request, response, new Object());

		assertThat(result).isFalse();
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
		assertThat(response.getContentAsString()).contains("Invalid Session-Token");
	}

	@Test
	void should_token_when_valid() throws Exception {
		request.addHeader("X-Session-Token", SESSION_TOKEN);
		when(queueSessionRepository.getSession(SESSION_TOKEN)).thenReturn(QueueSessionFixture.get(SESSION_TOKEN));

		boolean result = queueSessionInterceptor.preHandle(request, response, new Object());

		assertThat(result).isTrue();
		assertThat(request.getAttribute("sessionToken")).isEqualTo(SESSION_TOKEN);
	}
}
