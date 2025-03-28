package com.kbo.seatblock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.seatblock.entity.SeatBlock;
import com.kbo.seatblock.repository.SeatBlockRepository;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.service.StadiumService;

@Service
public class SeatBlockService {
	private final SeatBlockRepository seatBlockRepository;
	private final StadiumService stadiumService;

	public SeatBlockService(SeatBlockRepository seatBlockRepository, StadiumService stadiumService) {
		this.seatBlockRepository = seatBlockRepository;
		this.stadiumService = stadiumService;
	}

	@Transactional
	public SeatBlock save(String name, int seatCount, long stadiumId) {
		validateInputs(name, seatCount);

		if (seatBlockRepository.existsByName(name)) {
			throw new CustomAlreadyExistsException("Already exists name: " + name);
		}

		Stadium stadium = stadiumService.getStadium(stadiumId);
		return seatBlockRepository.save(new SeatBlock(name, seatCount, stadium));
	}

	private void validateInputs(String name, int seatCount) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException("Invalid seat block name: " + name);
		}
		if (seatCount <= 0) {
			throw new IllegalArgumentException("seatCount must be greater than 0.");
		}
	}
}
