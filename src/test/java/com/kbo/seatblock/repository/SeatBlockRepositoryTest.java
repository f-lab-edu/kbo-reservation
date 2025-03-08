package com.kbo.seatblock.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kbo.fixture.SeatBlockFixture;
import com.kbo.fixture.StadiumFixture;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.repository.StadiumRepository;

@SpringBootTest
@Transactional
class SeatBlockRepositoryTest {
	@Autowired
	private StadiumRepository stadiumRepository;

	@Autowired
	private SeatBlockRepository seatBlockRepository;

	private static final String NAME = "307";
	private static final int SEAT_COUNT = 1_000;

	private Stadium stadium;

	@BeforeEach
	void setUp() {
		stadium = stadiumRepository.save(StadiumFixture.get());
	}

	@Test
	void should_findSeatBlock_when_validName() {
		seatBlockRepository.save(SeatBlockFixture.get(NAME, SEAT_COUNT, stadium));

		boolean result = seatBlockRepository.existsByName(NAME);

		assertThat(result).isTrue();
	}
}
