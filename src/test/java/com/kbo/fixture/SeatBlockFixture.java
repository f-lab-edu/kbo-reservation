package com.kbo.fixture;

import com.kbo.seatblock.entity.SeatBlock;
import com.kbo.seatgrade.entity.SeatGrade;
import com.kbo.stadium.entity.Stadium;

public class SeatBlockFixture {
	public static SeatBlock get(String name, int seatCount, Stadium stadium, SeatGrade seatGrade) {
		return new SeatBlock(name, seatCount, stadium, seatGrade);
	}
}
