package com.kbo.seatblock.controller.response;

import com.kbo.seatblock.entity.SeatBlock;

public record SeatBlockResponse(
	long id, String name, int seatCount,
	String stadiumName, int stadiumTotalCapacity, int stadiumAllocatableSeatCount
) {
	public static SeatBlockResponse from(SeatBlock seatBlock) {
		return new SeatBlockResponse(
			seatBlock.getId(),
			seatBlock.getName(),
			seatBlock.getSeatCount(),
			seatBlock.getStadium().getName(),
			seatBlock.getStadium().getCapacity(),
			seatBlock.getStadium().getAllocatedSeatCount()
		);
	}
}
