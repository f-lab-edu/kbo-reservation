package com.kbo.stadium.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kbo.stadium.entity.Stadium;

@SpringBootTest
class StadiumRepositoryTest {
	@Autowired
	private StadiumRepository stadiumRepository;

	@Test
	void could_findStadium_when_validName() {
		Stadium stadium = stadiumRepository.save(new Stadium("잠실야구장", 25000));

		Optional<Stadium> result = stadiumRepository.findByName("잠실야구장");

		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo(stadium.getName());
		assertThat(result.get().getCapacity()).isEqualTo(stadium.getCapacity());
	}

	@Test
	void should_saveStadium_when_validInput() {
		Stadium stadium = new Stadium("잠실야구장", 25000);

		Stadium result = stadiumRepository.save(stadium);

		assertThat(result.getName()).isEqualTo(stadium.getName());
		assertThat(result.getCapacity()).isEqualTo(stadium.getCapacity());
	}
}
