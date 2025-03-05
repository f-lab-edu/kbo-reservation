package com.kbo.seatblock.controller.request;

public class SeatBlockRequest {
	private String name;
	private int seatCount;
	private long stadiumId;

	public String getName() {
		return name;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public long getStadiumId() {
		return stadiumId;
	}
}
