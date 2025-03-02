package com.kbo.team.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.repository.StadiumRepository;
import com.kbo.team.entity.Team;

@SpringBootTest
class TeamRepositoryTest {
	@Autowired
	private StadiumRepository stadiumRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Test
	void should_saveTeam_when_validInput() {
		Stadium stadium = stadiumRepository.save(new Stadium("잠실야구장", 25000));
		Team team = new Team("두산베어스", stadium);

		Team result = teamRepository.save(team);

		assertThat(result.getName()).isEqualTo(team.getName());
		assertThat(result.getStadium().getName()).isEqualTo(stadium.getName());
		assertThat(result.getStadium().getCapacity()).isEqualTo(stadium.getCapacity());
	}
}
