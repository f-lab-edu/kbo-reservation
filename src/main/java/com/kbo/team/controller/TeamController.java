package com.kbo.team.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbo.team.controller.request.TeamRequest;
import com.kbo.team.controller.response.TeamResponse;
import com.kbo.team.service.TeamService;

@RestController
@RequestMapping("/teams")
public class TeamController {
	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@PostMapping
	public TeamResponse save(@RequestBody TeamRequest teamRequest) {
		return TeamResponse.from(teamService.save(teamRequest.getName(), teamRequest.getStadiumId()));
	}
}
