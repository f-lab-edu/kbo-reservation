package com.kbo.seatblock.controller.response;

import com.kbo.seatblock.entity.SeatBlock;

public class SeatBlockDetailResponse {
	private final long id;
	private final String name;
	private final int seatCount;
	private final String stadiumName;
	private final int stadiumTotalCapacity;
	private final int stadiumAllocatableSeatCount;

	public SeatBlockDetailResponse(
		long id, String name, int seatCount, String stadiumName,
		int stadiumTotalCapacity, int stadiumAllocatableSeatCount
	) {
		this.id = id;
		this.name = name;
		this.seatCount = seatCount;
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

	public int getSeatCount() {
		return seatCount;
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
