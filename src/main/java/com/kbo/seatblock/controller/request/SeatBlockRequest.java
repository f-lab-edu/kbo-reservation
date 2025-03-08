package com.kbo.seatblock.controller.request;

public record SeatBlockRequest(
	String name, int seatCount, long stadiumId
) {
}
