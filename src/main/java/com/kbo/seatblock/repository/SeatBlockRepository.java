package com.kbo.seatblock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kbo.seatblock.entity.SeatBlock;

@Repository
public interface SeatBlockRepository extends JpaRepository<SeatBlock, Long> {
	boolean existsByName(String name);
}
