package com.kbo.seatblock.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.fixture.StadiumFixture;
import com.kbo.seatblock.entity.SeatBlock;
import com.kbo.seatblock.repository.SeatBlockRepository;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.service.StadiumService;

@ExtendWith(MockitoExtension.class)
class SeatBlockServiceTest {
	@Mock
	private SeatBlockRepository seatBlockRepository;

	@Mock
	private StadiumService stadiumService;

	@InjectMocks
	private SeatBlockService seatBlockService;

	private static final String NAME = "307";
	private static final int SEAT_COUNT = 1_000;
	private static final long STADIUM_ID = 1L;
	private static final Stadium STADIUM = StadiumFixture.get();

	@Test
	void should_getSeatBlockList_when_valid() {
		when(seatBlockRepository.findByStadiumId(anyLong())).thenReturn(List.of(
			new SeatBlock(NAME, SEAT_COUNT, STADIUM),
			new SeatBlock("308", SEAT_COUNT, STADIUM)));

		List<SeatBlock> result = seatBlockService.getList(STADIUM_ID);

		assertThat(result.size()).isEqualTo(2);
	}

	@Test
	void should_throwException_when_invalidInput() {
		assertThatThrownBy(() -> seatBlockService.save("", SEAT_COUNT, STADIUM_ID))
			.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> seatBlockService.save(NAME, -1, STADIUM_ID))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void should_throwException_when_duplicateName() {
		when(seatBlockRepository.existsByName(anyString())).thenReturn(true);

		assertThatThrownBy(() -> seatBlockService.save(NAME, SEAT_COUNT, STADIUM_ID))
			.isInstanceOf(CustomAlreadyExistsException.class);
	}

	@Test
	void should_throwException_when_overSeatCount() {
		Stadium stadium = StadiumFixture.get();
		stadium.allocateSeatCount(25_000);
		when(stadiumService.getStadium(anyLong())).thenReturn(stadium);

		assertThatThrownBy(() -> seatBlockService.save(NAME, SEAT_COUNT, STADIUM_ID))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("Stadium total capacity exceeded.");
	}

	@Test
	void should_saveSeatBlock_when_validInput() {
		when(stadiumService.getStadium(anyLong())).thenReturn(STADIUM);
		when(seatBlockRepository.save(any(SeatBlock.class))).thenReturn(
			new SeatBlock(NAME, SEAT_COUNT, STADIUM));

		SeatBlock result = seatBlockService.save(NAME, SEAT_COUNT, STADIUM_ID);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(NAME);
		assertThat(result.getSeatCount()).isEqualTo(SEAT_COUNT);
	}
}
