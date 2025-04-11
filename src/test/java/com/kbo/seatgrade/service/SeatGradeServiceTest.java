package com.kbo.seatgrade.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kbo.exception.CustomNotExistsException;
import com.kbo.fixture.SeatGradeFixture;
import com.kbo.fixture.StadiumFixture;
import com.kbo.seatgrade.constant.SeatSide;
import com.kbo.seatgrade.entity.SeatGrade;
import com.kbo.seatgrade.repository.SeatGradeRepository;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.service.StadiumService;

@ExtendWith(MockitoExtension.class)
class SeatGradeServiceTest {
	@Mock
	private SeatGradeRepository seatGradeRepository;

	@Mock
	private StadiumService stadiumService;

	@InjectMocks
	private SeatGradeService seatGradeService;

	@Test
	void 존재하지_않는_경기장_ID로_저장시_예외발생() {
		Long invalidStadiumId = 999L;
		when(stadiumService.getStadium(invalidStadiumId))
			.thenThrow(new CustomNotExistsException("Not exists id: " + invalidStadiumId));

		assertThatThrownBy(() ->
			seatGradeService.save("블루석", SeatSide.FIRST_BASE, 22_000, invalidStadiumId))
			.isInstanceOf(CustomNotExistsException.class)
			.hasMessage("Not exists id: " + invalidStadiumId);
	}

	@Test
	void 존재하지_않는_좌석_등급_ID로_조회시_예외발생() {
		Long invalidSeatId = 100L;
		when(seatGradeRepository.findById(invalidSeatId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> seatGradeService.getSeatGrade(invalidSeatId))
			.isInstanceOf(CustomNotExistsException.class)
			.hasMessage("Not exists id: " + invalidSeatId);
	}

	@Test
	void 좌석_등급_저장_성공() {
		Long stadiumId = 100L;
		Stadium stadium = StadiumFixture.get();
		SeatGrade seatGrade = SeatGradeFixture.get(stadium);

		when(stadiumService.getStadium(stadiumId)).thenReturn(stadium);
		when(seatGradeRepository.save(any())).thenReturn(seatGrade);

		SeatGrade result = seatGradeService.save("익사이팅존", SeatSide.FIRST_BASE, 28_000, stadiumId);

		assertThat(result.getName()).isEqualTo(seatGrade.getName());
		assertThat(result.getSeatSide()).isEqualTo(seatGrade.getSeatSide());
		assertThat(result.getPrice()).isEqualTo(seatGrade.getPrice());
		assertThat(result.getStadium()).isEqualTo(stadium);
	}

	@Test
	void 좌석_등급_단건_조회_성공() {
		Long id = 1L;
		SeatGrade seatGrade = SeatGradeFixture.get(mock(Stadium.class));
		when(seatGradeRepository.findById(id)).thenReturn(Optional.of(seatGrade));

		SeatGrade result = seatGradeService.getSeatGrade(id);

		assertThat(result.getName()).isEqualTo(seatGrade.getName());
	}
}
