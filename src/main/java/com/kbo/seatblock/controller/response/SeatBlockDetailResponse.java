package com.kbo.seatblock.controller.response;

import com.kbo.seatblock.entity.SeatBlock;

public record SeatBlockDetailResponse(
	long id, String name, int seatCount,
	String stadiumName, int stadiumTotalCapacity, int stadiumAllocatableSeatCount
) {
	public static SeatBlockDetailResponse from(SeatBlock seatBlock) {
		return new SeatBlockDetailResponse(
			seatBlock.getId(),
			seatBlock.getName(),
			seatBlock.getSeatCount(),
			seatBlock.getStadium().getName(),
			seatBlock.getStadium().getCapacity(),
			seatBlock.getStadium().getAllocatedSeatCount()
		);
	}
}
