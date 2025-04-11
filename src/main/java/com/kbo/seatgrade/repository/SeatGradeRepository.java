package com.kbo.seatgrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbo.seatgrade.entity.SeatGrade;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {
}
