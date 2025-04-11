package com.kbo.seatgrade.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		Stadium stadium = stadiumService.getStadium(stadiumId);
		return seatGradeRepository.save(new SeatGrade(name, seatSide, price, stadium));
	}
}
