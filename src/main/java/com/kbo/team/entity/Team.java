package com.kbo.team.entity;

import com.kbo.stadium.entity.Stadium;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "team")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stadium_id")
	private Stadium stadium;

	protected Team() {

	}

	public Team(String name, Stadium stadium) {
		this.name = name;
		setStadium(stadium);
	}

	public String getName() {
		return name;
	}

	public Stadium getStadium() {
		return stadium;
	}

	private void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}
}
