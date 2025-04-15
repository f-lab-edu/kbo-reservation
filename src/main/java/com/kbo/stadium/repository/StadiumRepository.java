package com.kbo.stadium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kbo.stadium.entity.Stadium;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
	boolean existsByName(String name);
}
