package com.kbo.stadium.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbo.stadium.controller.request.StadiumRequest;
import com.kbo.stadium.controller.response.StadiumResponse;
import com.kbo.stadium.service.StadiumService;

@RestController
@RequestMapping("/stadiums")
public class StadiumController {
	private final StadiumService stadiumService;

	public StadiumController(StadiumService stadiumService) {
		this.stadiumService = stadiumService;
	}

	@PostMapping
	public StadiumResponse save(@RequestBody StadiumRequest stadiumRequest) {
		return StadiumResponse.from(stadiumService.save(stadiumRequest.getName(), stadiumRequest.getCapacity()));
	}
}
