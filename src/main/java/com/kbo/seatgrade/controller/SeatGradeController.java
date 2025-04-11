package com.kbo.seatgrade.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbo.seatgrade.controller.request.SeatGradeRequest;
import com.kbo.seatgrade.controller.response.SeatGradeResponse;
import com.kbo.seatgrade.service.SeatGradeService;

@RestController
@RequestMapping("/seatGrades")
public class SeatGradeController {
	private final SeatGradeService seatGradeService;

	public SeatGradeController(SeatGradeService seatGradeService) {
		this.seatGradeService = seatGradeService;
	}

	@GetMapping("/{id}")
	public SeatGradeResponse getGrade(@PathVariable("id") long id) {
		return SeatGradeResponse.from(seatGradeService.getSeatGrade(id));
	}

	@PostMapping
	public SeatGradeResponse save(@RequestBody SeatGradeRequest seatGradeRequest) {
		return SeatGradeResponse.from(seatGradeService.save(
			seatGradeRequest.name(),
			seatGradeRequest.seatSide(),
			seatGradeRequest.price(),
			seatGradeRequest.stadiumId()));
	}
}
