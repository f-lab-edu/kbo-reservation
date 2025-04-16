package com.kbo.fixture;

import com.kbo.seatgrade.constant.SeatSide;
import com.kbo.seatgrade.entity.SeatGrade;
import com.kbo.stadium.entity.Stadium;

public class SeatGradeFixture {
	public static final String NAME = "익사이팅존";
	public static final SeatSide SEAT_SIDE = SeatSide.FIRST_BASE;
	public static final int PRICE = 28_000;

	public static SeatGrade get(Stadium stadium) {
		return new SeatGrade(NAME, SEAT_SIDE, PRICE, stadium);
	}
}
