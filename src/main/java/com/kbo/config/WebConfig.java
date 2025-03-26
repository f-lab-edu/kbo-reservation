package com.kbo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kbo.config.interceptor.QueueSessionInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final QueueSessionInterceptor queueSessionInterceptor;

	public WebConfig(QueueSessionInterceptor queueSessionInterceptor) {
		this.queueSessionInterceptor = queueSessionInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(queueSessionInterceptor)
			.addPathPatterns("/queue/subscribe");
	}
}
