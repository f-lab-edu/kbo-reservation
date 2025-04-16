package com.kbo.seatblock.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kbo.fixture.SeatBlockFixture;
import com.kbo.fixture.SeatGradeFixture;
import com.kbo.fixture.StadiumFixture;
import com.kbo.seatgrade.entity.SeatGrade;
import com.kbo.seatgrade.repository.SeatGradeRepository;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.repository.StadiumRepository;

@SpringBootTest
@Transactional
class SeatBlockRepositoryTest {
	@Autowired
	private StadiumRepository stadiumRepository;

	@Autowired
	private SeatBlockRepository seatBlockRepository;

	@Autowired
	private SeatGradeRepository seatGradeRepository;

	private static final String NAME = "307";
	private static final int SEAT_COUNT = 1_000;

	private Stadium stadium;
	private SeatGrade seatGrade;

	@BeforeEach
	void setUp() {
		stadium = stadiumRepository.save(StadiumFixture.get());
		seatGrade = seatGradeRepository.save(SeatGradeFixture.get(stadium));
	}

	@Test
	void 좌석블럭_존재_확인_성공() {
		seatBlockRepository.save(SeatBlockFixture.get(NAME, SEAT_COUNT, stadium, seatGrade));

		boolean result = seatBlockRepository.existsByName(NAME);

		assertThat(result).isTrue();
	}
}
