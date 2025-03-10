package com.kbo.stadium.controller.response;

import com.kbo.stadium.entity.Stadium;

public record StadiumResponse(
	long id, String name, int capacity
) {
	public static StadiumResponse from(Stadium stadium) {
		return new StadiumResponse(
			stadium.getId(),
			stadium.getName(),
			stadium.getCapacity()
		);
	}
}
