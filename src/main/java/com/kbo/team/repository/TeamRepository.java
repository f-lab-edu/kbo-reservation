package com.kbo.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbo.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	boolean existsByName(String name);
}
