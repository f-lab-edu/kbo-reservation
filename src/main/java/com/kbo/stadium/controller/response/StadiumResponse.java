package com.kbo.stadium.controller.response;

import com.kbo.stadium.entity.Stadium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StadiumResponse {
	private long id;
	private String name;
	private int capacity;;

	public static StadiumResponse from(Stadium stadium) {
		return StadiumResponse.builder()
			.id(stadium.getId())
			.name(stadium.getName())
			.capacity(stadium.getCapacity())
			.build();
	}
}
