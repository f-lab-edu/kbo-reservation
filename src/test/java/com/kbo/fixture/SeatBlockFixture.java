package com.kbo.fixture;

import com.kbo.seatblock.entity.SeatBlock;
import com.kbo.stadium.entity.Stadium;

public class SeatBlockFixture {
	public static SeatBlock get(String name, long seatCount, Stadium stadium) {
		return new SeatBlock(name, seatCount, stadium);
	}
}
