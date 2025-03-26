package com.kbo.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kbo.queue.controller.response.QueueSession;
import com.kbo.queue.repository.QueueSessionRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class QueueSessionInterceptor implements HandlerInterceptor {
	private final QueueSessionRepository queueSessionRepository;

	public QueueSessionInterceptor(QueueSessionRepository queueSessionRepository) {
		this.queueSessionRepository = queueSessionRepository;
	}

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) throws Exception {
		String sessionToken = request.getHeader("X-Session-Token");

		if (sessionToken == null || sessionToken.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Missing Session-Token");
			return false;
		}

		QueueSession queueSession = queueSessionRepository.getSession(sessionToken);
		if (queueSession == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid Session-Token");
			return false;
		}

		request.setAttribute("sessionToken", sessionToken);

		return true;
	}
}
