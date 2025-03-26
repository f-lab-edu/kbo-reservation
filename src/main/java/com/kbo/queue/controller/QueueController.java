package com.kbo.queue.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kbo.queue.controller.request.QueueRequest;
import com.kbo.queue.controller.response.QueueSession;
import com.kbo.queue.service.QueueService;
import com.kbo.sse.service.SseService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/queue")
public class QueueController {
	private final QueueService queueService;
	private final SseService sseService;

	public QueueController(QueueService queueService, SseService sseService) {
		this.queueService = queueService;
		this.sseService = sseService;
	}

	@PostMapping("/join")
	public QueueSession join(@RequestBody QueueRequest queueRequest) {
		return queueService.join(queueRequest.gameId(), queueRequest.userId());
	}

	@GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(HttpServletRequest request) {
		return sseService.add((String)request.getAttribute("sessionToken"));
	}
}
