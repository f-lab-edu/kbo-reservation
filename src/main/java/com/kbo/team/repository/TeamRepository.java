package com.kbo.team.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kbo.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	Optional<Team> findByName(String name);
}
