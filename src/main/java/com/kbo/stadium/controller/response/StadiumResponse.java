package com.kbo.stadium.controller.response;

import com.kbo.stadium.entity.Stadium;

public class StadiumResponse {
	private final long id;
	private final String name;
	private final int capacity;

	public StadiumResponse(long id, String name, int capacity) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public static StadiumResponse from(Stadium stadium) {
		return new StadiumResponse(
			stadium.getId(),
			stadium.getName(),
			stadium.getCapacity()
		);
	}
}
