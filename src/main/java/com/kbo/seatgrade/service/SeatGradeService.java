package com.kbo.seatgrade.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kbo.exception.CustomAlreadyExistsException;
import com.kbo.exception.CustomNotExistsException;
import com.kbo.seatgrade.constant.SeatSide;
import com.kbo.seatgrade.entity.SeatGrade;
import com.kbo.seatgrade.repository.SeatGradeRepository;
import com.kbo.stadium.entity.Stadium;
import com.kbo.stadium.service.StadiumService;

@Service
public class SeatGradeService {
	private final SeatGradeRepository seatGradeRepository;
	private final StadiumService stadiumService;

	public SeatGradeService(SeatGradeRepository seatGradeRepository, StadiumService stadiumService) {
		this.seatGradeRepository = seatGradeRepository;
		this.stadiumService = stadiumService;
	}

	public SeatGrade getSeatGrade(long id) {
		return seatGradeRepository.findById(id)
			.orElseThrow(() -> new CustomNotExistsException("Not exists id: " + id));
	}

	@Transactional
	public SeatGrade save(String name, SeatSide seatSide, int price, long stadiumId) {
		// 검증 수행
		validateInputs(name, seatSide, price);

		Stadium stadium = stadiumService.getStadium(stadiumId);

		if (seatGradeRepository.existsByNameAndSeatSideAndStadium(name, seatSide, stadium)) {
			throw new CustomAlreadyExistsException("Already exists name: " + name + ", seatSide: " + seatSide);
		}

		return seatGradeRepository.save(new SeatGrade(name, seatSide, price, stadium));
	}

	private void validateInputs(String name, SeatSide seatSide, int price) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException("Invalid SeatGrade name: " + name);
		}
		if (seatSide == null) {
			throw new IllegalArgumentException("Invalid SeatGrade seatSide.");
		}
		if (price <= 0) {
			throw new IllegalArgumentException("Invalid SeatGrade price: " + price);
		}
	}
}
