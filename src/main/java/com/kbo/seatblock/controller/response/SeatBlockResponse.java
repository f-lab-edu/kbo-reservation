package com.kbo.seatblock.controller.response;

import com.kbo.seatblock.entity.SeatBlock;

public class SeatBlockResponse {
	private final long id;
	private final String name;
	private final String stadiumName;
	private final int stadiumTotalCapacity;
	private final int stadiumAllocatableSeatCount;

	public SeatBlockResponse(
		long id, String name, String stadiumName,
		int stadiumTotalCapacity, int stadiumAllocatableSeatCount
	) {
		this.id = id;
		this.name = name;
		this.stadiumName = stadiumName;
		this.stadiumTotalCapacity = stadiumTotalCapacity;
		this.stadiumAllocatableSeatCount = stadiumAllocatableSeatCount;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStadiumName() {
		return stadiumName;
	}

	public int getStadiumTotalCapacity() {
		return stadiumTotalCapacity;
	}

	public int getStadiumAllocatableSeatCount() {
		return stadiumAllocatableSeatCount;
	}

	public static SeatBlockResponse from(SeatBlock seatBlock) {
		return new SeatBlockResponse(
			seatBlock.getId(),
			seatBlock.getName(),
			seatBlock.getStadium().getName(),
			seatBlock.getStadium().getCapacity(),
			seatBlock.getStadium().getAllocatedSeatCount()
		);
	}
}
