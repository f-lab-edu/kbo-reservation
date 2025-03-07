package com.kbo.seatblock.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbo.seatblock.controller.request.SeatBlockRequest;
import com.kbo.seatblock.controller.response.SeatBlockDetailResponse;
import com.kbo.seatblock.controller.response.SeatBlockResponse;
import com.kbo.seatblock.service.SeatBlockService;

@RestController
@RequestMapping("/seatBlocks")
public class SeatBlockController {
	private final SeatBlockService seatBlockService;

	public SeatBlockController(SeatBlockService seatBlockService) {
		this.seatBlockService = seatBlockService;
	}

	@GetMapping("/{stadiumId}")
	public List<SeatBlockResponse> getList(@PathVariable(name = "stadiumId") long stadiumId) {
		return seatBlockService.getList(stadiumId)
			.stream()
			.map(SeatBlockResponse::from)
			.collect(Collectors.toList());
	}

	@PostMapping
	public SeatBlockDetailResponse save(@RequestBody SeatBlockRequest seatBlockRequest) {
		return SeatBlockDetailResponse.from(
			seatBlockService.save(
				seatBlockRequest.name(),
				seatBlockRequest.seatCount(),
				seatBlockRequest.stadiumId()
			)
		);
	}
}
