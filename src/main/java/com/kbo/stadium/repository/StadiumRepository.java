package com.kbo.stadium.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbo.stadium.entity.Stadium;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
	Optional<Stadium> findByName(String name);
}
