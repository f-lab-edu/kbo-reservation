package com.kbo.seatblock.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.fixture.SeatGradeFixture;
import com.kbo.fixture.StadiumFixture;
import com.kbo.seatblock.entity.SeatBlock;
import com.kbo.seatblock.repository.SeatBlockRepository;
import com.kbo.seatgrade.entity.SeatGrade;
import com.kbo.seatgrade.service.SeatGradeService;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.service.StadiumService;

@ExtendWith(MockitoExtension.class)
class SeatBlockServiceTest {
	@Mock
	private SeatBlockRepository seatBlockRepository;

	@Mock
	private StadiumService stadiumService;

	@Mock
	private SeatGradeService seatGradeService;

	@InjectMocks
	private SeatBlockService seatBlockService;

	private static final String NAME = "307";
	private static final int SEAT_COUNT = 1_000;
	private static final long STADIUM_ID = 1L;
	private static final long SEAT_GRADE_ID = 1L;
	private static final Stadium STADIUM = StadiumFixture.get();
	private static final SeatGrade SEAT_GRADE = SeatGradeFixture.get(STADIUM);

	@Test
	void should_throwException_when_invalidInput() {
		assertThatThrownBy(() -> seatBlockService.save("", SEAT_COUNT, STADIUM_ID, SEAT_GRADE_ID))
			.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> seatBlockService.save(NAME, -1, STADIUM_ID, SEAT_GRADE_ID))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void should_throwException_when_duplicateName() {
		when(seatBlockRepository.existsByName(anyString())).thenReturn(true);

		assertThatThrownBy(() -> seatBlockService.save(NAME, SEAT_COUNT, STADIUM_ID, SEAT_GRADE_ID))
			.isInstanceOf(CustomAlreadyExistsException.class);
	}

	@Test
	void should_throwException_when_overSeatCount() {
		Stadium stadium = StadiumFixture.get();
		SeatGrade seatGrade = SeatGradeFixture.get(stadium);
		stadium.allocateSeatCount(25_000);
		when(stadiumService.getStadium(anyLong())).thenReturn(stadium);
		when(seatGradeService.getSeatGrade(anyLong())).thenReturn(seatGrade);

		assertThatThrownBy(() -> seatBlockService.save(NAME, SEAT_COUNT, STADIUM_ID, SEAT_GRADE_ID))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("Stadium total capacity exceeded.");
	}

	@Test
	void should_saveSeatBlock_when_validInput() {
		when(stadiumService.getStadium(anyLong())).thenReturn(STADIUM);
		when(seatGradeService.getSeatGrade(anyLong())).thenReturn(SEAT_GRADE);
		when(seatBlockRepository.save(any(SeatBlock.class))).thenReturn(
			new SeatBlock(NAME, SEAT_COUNT, STADIUM, SEAT_GRADE));

		SeatBlock result = seatBlockService.save(NAME, SEAT_COUNT, STADIUM_ID, SEAT_GRADE_ID);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(NAME);
		assertThat(result.getSeatCount()).isEqualTo(SEAT_COUNT);
	}
}
