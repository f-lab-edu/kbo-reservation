package com.kbo.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kbo.team.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	boolean existsByName(String name);
}
