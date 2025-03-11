package com.kbo.team.controller.response;

import com.kbo.team.entity.Team;

public record TeamResponse(
	String teamName, String stadiumName, int stadiumCapacity
) {
	public static TeamResponse from(Team team) {
		return new TeamResponse(
			team.getName(),
			team.getStadium().getName(),
			team.getStadium().getCapacity()
		);
	}
}
