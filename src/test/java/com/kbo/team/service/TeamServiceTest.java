package com.kbo.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.service.StadiumService;
import com.kbo.team.entity.Team;
import com.kbo.team.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
	@Mock
	private TeamRepository teamRepository;

	@Mock
	private StadiumService stadiumService;

	@InjectMocks
	private TeamService teamService;

	private static final String NAME = "두산베어스";
	private static final long STADIUM_ID = 1L;

	private Team team;

	@BeforeEach
	void setUp() {
		Stadium stadium = new Stadium("잠실야구장", 25_000);
		team = new Team(NAME, stadium);
	}

	@Test
	void 팀_중복_저장할_경우_예외발생() {
		when(teamRepository.existsByName(anyString())).thenReturn(true);

		assertThatThrownBy(() -> teamService.save(NAME, STADIUM_ID))
			.isInstanceOf(CustomAlreadyExistsException.class);

		verify(teamRepository, never()).save(any(Team.class));
	}

	@Test
	void 팀_저장_성공() {
		Stadium stadium = team.getStadium();

		when(stadiumService.getStadium(anyLong())).thenReturn(stadium);
		when(teamRepository.save(any(Team.class))).thenReturn(team);

		Team result = teamService.save(NAME, STADIUM_ID);

		assertThat(result.getName()).isEqualTo(team.getName());
		assertThat(result.getStadium().getName()).isEqualTo(stadium.getName());
		assertThat(result.getStadium().getCapacity()).isEqualTo(stadium.getCapacity());

		verify(teamRepository, times(1)).save(any(Team.class));
	}
}
