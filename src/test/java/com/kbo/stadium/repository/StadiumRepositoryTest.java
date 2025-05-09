package com.kbo.stadium.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kbo.stadium.entity.Stadium;

@SpringBootTest
@Transactional
class StadiumRepositoryTest {
	@Autowired
	private StadiumRepository stadiumRepository;

	private static final String NAME = "잠실야구장";
	private static final int CAPACITY = 25_000;

	@Test
	void 경기장_존재_확인_성공() {
		stadiumRepository.save(new Stadium(NAME, CAPACITY));

		boolean result = stadiumRepository.existsByName(NAME);

		assertThat(result).isTrue();
	}

	@Test
	void 경기장_저장_성공() {
		Stadium stadium = new Stadium(NAME, CAPACITY);

		Stadium result = stadiumRepository.save(stadium);

		assertThat(result.getName()).isEqualTo(stadium.getName());
		assertThat(result.getCapacity()).isEqualTo(stadium.getCapacity());
	}
}
