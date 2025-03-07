package com.kbo.seatblock.controller.request;

public record SeatBlockRequest(
	String name, long seatCount, long stadiumId
) {
}
