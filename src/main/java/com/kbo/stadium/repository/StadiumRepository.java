package com.kbo.stadium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbo.stadium.entity.Stadium;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
	boolean existsByName(String name);
}
