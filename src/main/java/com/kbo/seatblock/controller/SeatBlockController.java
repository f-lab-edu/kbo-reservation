package com.kbo.seatblock.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbo.seatblock.controller.request.SeatBlockRequest;
import com.kbo.seatblock.controller.response.SeatBlockResponse;
import com.kbo.seatblock.service.SeatBlockService;

@RestController
@RequestMapping("/seatBlocks")
public class SeatBlockController {
	private final SeatBlockService seatBlockService;

	public SeatBlockController(SeatBlockService seatBlockService) {
		this.seatBlockService = seatBlockService;
	}

	@PostMapping
	public SeatBlockResponse save(@RequestBody SeatBlockRequest seatBlockRequest) {
		return SeatBlockResponse.from(
			seatBlockService.save(
				seatBlockRequest.name(),
				seatBlockRequest.seatCount(),
				seatBlockRequest.stadiumId()
			)
		);
	}
}
