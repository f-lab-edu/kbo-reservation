package com.kbo.stadium.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.exception.CustomNotExistsException;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.repository.StadiumRepository;

@Service
public class StadiumService {
	private final StadiumRepository stadiumRepository;

	public StadiumService(StadiumRepository stadiumRepository) {
		this.stadiumRepository = stadiumRepository;
	}

	public Stadium getStadium(long id) {
		return stadiumRepository.findById(id)
			.orElseThrow(() -> new CustomNotExistsException("Not exists id: " + id));
	}

	@Transactional
	public Stadium save(String name, int capacity) {
		// 검증 수행
		validateInputs(name, capacity);

		if (stadiumRepository.existsByName(name)) {
			throw new CustomAlreadyExistsException("Already exists name: " + name);
		}

		return stadiumRepository.save(new Stadium(name, capacity));
	}

	private void validateInputs(String name, int capacity) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException("Invalid stadium name: " + name);
		}
		if (capacity <= 0) {
			throw new IllegalArgumentException("Invalid stadium capacity: " + capacity);
		}
	}
}
