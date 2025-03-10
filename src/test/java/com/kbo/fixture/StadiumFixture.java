package com.kbo.fixture;

import com.kbo.stadium.entity.Stadium;

public class StadiumFixture {
	public static final String NAME = "잠실야구장";
	public static final int CAPACITY = 25_000;

	public static Stadium get() {
		return new Stadium(NAME, CAPACITY);
	}
}
