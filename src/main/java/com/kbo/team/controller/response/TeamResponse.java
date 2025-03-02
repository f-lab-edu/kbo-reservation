package com.kbo.team.controller.response;

import com.kbo.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TeamResponse {
	private String teamName;
	private String stadiumName;
	private int stadiumCapacity;

	public static TeamResponse from(Team team) {
		return TeamResponse.builder()
			.teamName(team.getName())
			.stadiumName(team.getStadium().getName())
			.stadiumCapacity(team.getStadium().getCapacity())
			.build();
	}
}
