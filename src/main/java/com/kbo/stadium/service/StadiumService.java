package com.kbo.stadium.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		if (stadiumRepository.findByName(name).isPresent()) {
			throw new CustomAlreadyExistsException("Already exists name: " + name);
		}

		return stadiumRepository.save(new Stadium(name, capacity));
	}
}
