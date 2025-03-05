package com.kbo.seatblock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbo.seatblock.entity.SeatBlock;

public interface SeatBlockRepository extends JpaRepository<SeatBlock, Long> {
	List<SeatBlock> findByStadiumId(long stadiumId);

	boolean existsByName(String name);
}
