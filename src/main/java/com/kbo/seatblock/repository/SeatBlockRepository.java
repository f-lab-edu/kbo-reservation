package com.kbo.seatblock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbo.seatblock.entity.SeatBlock;

public interface SeatBlockRepository extends JpaRepository<SeatBlock, Long> {
	boolean existsByName(String name);
}
