package com.kbo.team.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.repository.StadiumRepository;
import com.kbo.team.entity.Team;

@SpringBootTest
@Transactional
class TeamRepositoryTest {
	@Autowired
	private StadiumRepository stadiumRepository;

	@Autowired
	private TeamRepository teamRepository;

	private static final String NAME = "잠실야구장";
	private static final int CAPACITY = 25_000;

	@Test
	void 팀_저장_성공() {
		Stadium stadium = stadiumRepository.save(new Stadium(NAME, CAPACITY));
		Team team = new Team("두산베어스", stadium);

		Team result = teamRepository.save(team);

		assertThat(result.getName()).isEqualTo(team.getName());
		assertThat(result.getStadium().getName()).isEqualTo(stadium.getName());
		assertThat(result.getStadium().getCapacity()).isEqualTo(stadium.getCapacity());
	}
}
