package com.kbo.seatgrade.controller.response;

import com.kbo.seatgrade.entity.SeatGrade;

public record SeatGradeResponse(
	long id,
	String name,
	String seatSide,
	int price,
	long stadiumId,
	String displayName
) {
	public static SeatGradeResponse from(SeatGrade seatGrade) {
		return new SeatGradeResponse(
			seatGrade.getId(),
			seatGrade.getName(),
			seatGrade.getSeatSide().name(),
			seatGrade.getPrice(),
			seatGrade.getStadium().getId(),
			seatGrade.getDisplayName()
		);
	}
}
