package com.kbo.seatgrade.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kbo.fixture.SeatGradeFixture;
import com.kbo.fixture.StadiumFixture;
import com.kbo.seatgrade.entity.SeatGrade;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.repository.StadiumRepository;

@SpringBootTest
@Transactional
class SeatGradeRepositoryTest {
	@Autowired
	private SeatGradeRepository seatGradeRepository;

	@Autowired
	private StadiumRepository stadiumRepository;

	private Stadium stadium;

	@BeforeEach
	void setUp() {
		stadium = stadiumRepository.save(StadiumFixture.get());
	}

	@Test
	void should_throwException_when_invalidId() {
		Optional<SeatGrade> result = seatGradeRepository.findById(-1L);

		assertThat(result).isEmpty();
	}

	@Test
	void should_save_when_valid() {
		SeatGrade seatGrade = SeatGradeFixture.get(stadium);
		seatGradeRepository.save(seatGrade);

		Optional<SeatGrade> result = seatGradeRepository.findById(seatGrade.getId());

		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo(seatGrade.getName());
	}
}
