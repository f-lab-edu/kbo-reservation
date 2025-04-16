package com.kbo.seatgrade.constant;

public enum SeatSide {
	FIRST_BASE("1루"),
	THIRD_BASE("3루"),
	CENTER("중앙");

	private final String displayName;

	SeatSide(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
