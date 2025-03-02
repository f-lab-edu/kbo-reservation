package com.kbo.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.Optional;

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

	private Team team;

	@BeforeEach
	void setUp() {
		Stadium stadium = new Stadium("잠실야구장", 25000);
		team = new Team("두산베어스", stadium);
	}

	@Test
	void should_throwException_when_duplicateName() {
		when(teamRepository.findByName(anyString())).thenReturn(Optional.of(team));

		assertThatThrownBy(() -> teamService.save("두산베어스", 1L))
			.isInstanceOf(CustomAlreadyExistsException.class);

		verify(teamRepository, never()).save(any(Team.class));
	}

	@Test
	void should_saveTeam_when_validInput() {
		Stadium stadium = team.getStadium();

		when(stadiumService.getStadium(anyLong())).thenReturn(stadium);
		when(teamRepository.save(any(Team.class))).thenReturn(team);

		Team result = teamService.save("두산베어스", 1L);

		assertThat(result.getName()).isEqualTo(team.getName());
		assertThat(result.getStadium().getName()).isEqualTo(stadium.getName());
		assertThat(result.getStadium().getCapacity()).isEqualTo(stadium.getCapacity());

		verify(teamRepository, times(1)).save(any(Team.class));
	}
}
