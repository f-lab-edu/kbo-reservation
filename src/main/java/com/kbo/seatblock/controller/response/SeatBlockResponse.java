package com.kbo.seatblock.controller.response;

import com.kbo.seatblock.entity.SeatBlock;

public class SeatBlockResponse {
	private final long id;
	private final String name;

	public SeatBlockResponse(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static SeatBlockResponse from(SeatBlock seatBlock) {
		return new SeatBlockResponse(
			seatBlock.getId(),
			seatBlock.getName()
		);
	}
}
