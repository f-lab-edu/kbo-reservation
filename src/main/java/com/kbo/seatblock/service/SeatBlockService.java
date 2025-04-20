package com.kbo.seatblock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.seatblock.entity.SeatBlock;
import com.kbo.seatblock.repository.SeatBlockRepository;
import com.kbo.seatgrade.entity.SeatGrade;
import com.kbo.seatgrade.service.SeatGradeService;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.service.StadiumService;

@Service
public class SeatBlockService {
	private final SeatBlockRepository seatBlockRepository;
	private final StadiumService stadiumService;
	private final SeatGradeService seatGradeService;

	public SeatBlockService(
		SeatBlockRepository seatBlockRepository,
		StadiumService stadiumService,
		SeatGradeService seatGradeService
	) {
		this.seatBlockRepository = seatBlockRepository;
		this.stadiumService = stadiumService;
		this.seatGradeService = seatGradeService;
	}

	@Transactional
	public SeatBlock save(String name, int seatCount, long stadiumId, long seatGradeId) {
		validateInputs(name, seatCount);

		if (seatBlockRepository.existsByName(name)) {
			throw new CustomAlreadyExistsException("Already exists name: " + name);
		}

		Stadium stadium = stadiumService.getStadium(stadiumId);
		SeatGrade seatGrade = seatGradeService.getSeatGrade(seatGradeId);
		return seatBlockRepository.save(new SeatBlock(name, seatCount, stadium, seatGrade));
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
