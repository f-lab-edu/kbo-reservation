package com.kbo.stadium.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.repository.StadiumRepository;

@ExtendWith(MockitoExtension.class)
class StadiumServiceTest {
	@Mock
	private StadiumRepository stadiumRepository;

	@InjectMocks
	private StadiumService stadiumService;

	private static final String NAME = "잠실야구장";
	private static final int CAPACITY = 25_000;

	@Test
	void 경기장명_입력하지_않을_경우_예외발생() {
		assertThatThrownBy(() -> stadiumService.save("", CAPACITY))
			.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> stadiumService.save(NAME, -1))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 경기장_중복_저장할_경우_예외발생() {
		when(stadiumRepository.existsByName(NAME)).thenReturn(true);

		assertThatThrownBy(() -> stadiumService.save(NAME, CAPACITY))
			.isInstanceOf(CustomAlreadyExistsException.class);

		verify(stadiumRepository, never()).save(any(Stadium.class));
	}

	@Test
	void 경기장_저장_성공() {
		when(stadiumRepository.save(any(Stadium.class))).thenReturn(new Stadium(NAME, CAPACITY));

		Stadium result = stadiumService.save(NAME, CAPACITY);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(NAME);
		assertThat(result.getCapacity()).isEqualTo(CAPACITY);

		verify(stadiumRepository, times(1)).save(any(Stadium.class));
	}
}
