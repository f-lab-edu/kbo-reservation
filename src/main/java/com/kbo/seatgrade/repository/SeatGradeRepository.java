package com.kbo.seatgrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kbo.seatgrade.entity.SeatGrade;

@Repository
public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {
}
