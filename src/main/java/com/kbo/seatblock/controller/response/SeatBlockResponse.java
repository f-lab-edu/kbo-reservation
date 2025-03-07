package com.kbo.seatblock.controller.response;

import com.kbo.seatblock.entity.SeatBlock;

public record SeatBlockResponse(
	long id, String name
) {
	public static SeatBlockResponse from(SeatBlock seatBlock) {
		return new SeatBlockResponse(
			seatBlock.getId(),
			seatBlock.getName()
		);
	}
}
