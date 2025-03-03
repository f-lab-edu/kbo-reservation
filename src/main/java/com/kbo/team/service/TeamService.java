package com.kbo.team.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.service.StadiumService;
import com.kbo.team.entity.Team;
import com.kbo.team.repository.TeamRepository;

@Service
public class TeamService {
	private final TeamRepository teamRepository;
	private final StadiumService stadiumService;

	public TeamService(TeamRepository teamRepository, StadiumService stadiumService) {
		this.teamRepository = teamRepository;
		this.stadiumService = stadiumService;
	}

	@Transactional
	public Team save(String name, long stadiumId) {
		// 검증 수행
		validateInputs(name);

		Stadium stadium = stadiumService.getStadium(stadiumId);

		if (teamRepository.existsByName(name)) {
			throw new CustomAlreadyExistsException("Already exists name: " + name);
		}

		return teamRepository.save(new Team(name, stadium));
	}

	private void validateInputs(String name) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException("Invalid team name: " + name);
		}
	}
}
