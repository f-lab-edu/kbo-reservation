package com.kbo.seatgrade.controller.request;

import com.kbo.seatgrade.constant.SeatSide;

public record SeatGradeRequest(
	String name,
	SeatSide seatSide,
	int price,
	long stadiumId
) {
}
