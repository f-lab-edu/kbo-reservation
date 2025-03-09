package com.kbo.queue.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbo.queue.controller.request.QueueRequest;
import com.kbo.queue.controller.response.QueueResponse;
import com.kbo.queue.service.QueueService;

@RestController
@RequestMapping("/queue")
public class QueueController {
	private final QueueService queueService;

	public QueueController(QueueService queueService) {
		this.queueService = queueService;
	}

	@PostMapping("/join")
	public QueueResponse join(@RequestBody QueueRequest queueRequest) {
		return queueService.join(queueRequest.gameId(), queueRequest.userId());
	}
}
