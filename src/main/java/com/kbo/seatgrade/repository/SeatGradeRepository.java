package com.kbo.seatgrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kbo.seatgrade.constant.SeatSide;
import com.kbo.seatgrade.entity.SeatGrade;
import com.kbo.stadium.entity.Stadium;

@Repository
public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {
	boolean existsByNameAndSeatSideAndStadium(String name, SeatSide seatSide, Stadium stadium);
}
