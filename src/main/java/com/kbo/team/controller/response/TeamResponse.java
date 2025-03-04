package com.kbo.team.controller.response;

import com.kbo.team.entity.Team;

public class TeamResponse {
	private final String teamName;
	private final String stadiumName;
	private final int stadiumCapacity;

	public TeamResponse(String teamName, String stadiumName, int stadiumCapacity) {
		this.teamName = teamName;
		this.stadiumName = stadiumName;
		this.stadiumCapacity = stadiumCapacity;
	}

	public String getTeamName() {
		return teamName;
	}

	public String getStadiumName() {
		return stadiumName;
	}

	public int getStadiumCapacity() {
		return stadiumCapacity;
	}

	public static TeamResponse from(Team team) {
		return new TeamResponse(
			team.getName(),
			team.getStadium().getName(),
			team.getStadium().getCapacity()
		);
	}
}
