package com.kbo.seatgrade.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kbo.exception.CustomAlreadyExistsException;
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

	private static final String NAME = "익사이팅존";
	private static final SeatSide SEAT_SIDE = SeatSide.FIRST_BASE;
	private static final int PRICE = 28_000;
	private static final Long STADIUM_ID = 1001L;

	@Test
	void 존재하지_않는_경기장_ID로_저장시_예외발생() {
		Long invalidStadiumId = 999L;

		when(stadiumService.getStadium(invalidStadiumId))
			.thenThrow(new CustomNotExistsException("Not exists id: " + invalidStadiumId));

		assertThatThrownBy(() ->
			seatGradeService.save(NAME, SEAT_SIDE, PRICE, invalidStadiumId))
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
	void 좌석_등급_저장시_유효하지_않은_이름이면_예외발생() {
		assertThatThrownBy(() -> seatGradeService.save("    ", SEAT_SIDE, PRICE, STADIUM_ID))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Invalid SeatGrade name");
	}

	@Test
	void 좌석_등급_저장시_좌석방향_null이면_예외발생() {
		assertThatThrownBy(() -> seatGradeService.save(NAME, null, PRICE, STADIUM_ID))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Invalid SeatGrade seatSide.");
	}

	@Test
	void 좌석_등급_저장시_가격이_0이하이면_예외발생() {
		assertThatThrownBy(() -> seatGradeService.save(NAME, SEAT_SIDE, 0, STADIUM_ID))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Invalid SeatGrade price");
	}

	@Test
	void 동일한_이름과_좌석방향_경기장이_이미_존재하면_예외발생() {
		Stadium stadium = StadiumFixture.get();

		when(stadiumService.getStadium(STADIUM_ID)).thenReturn(stadium);
		when(seatGradeRepository.existsByNameAndSeatSideAndStadium(NAME, SEAT_SIDE, stadium))
			.thenReturn(true);

		assertThatThrownBy(() -> seatGradeService.save(NAME, SEAT_SIDE, PRICE, STADIUM_ID))
			.isInstanceOf(CustomAlreadyExistsException.class)
			.hasMessageContaining("Already exists name");
	}

	@Test
	void 좌석_등급_저장_성공() {
		Stadium stadium = StadiumFixture.get();
		SeatGrade seatGrade = SeatGradeFixture.get(stadium);

		when(stadiumService.getStadium(STADIUM_ID)).thenReturn(stadium);
		when(seatGradeRepository.save(any())).thenReturn(seatGrade);

		SeatGrade result = seatGradeService.save(NAME, SEAT_SIDE, PRICE, STADIUM_ID);

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
