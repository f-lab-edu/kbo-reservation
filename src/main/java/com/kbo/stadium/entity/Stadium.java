package com.kbo.stadium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stadium")
public class Stadium {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stadium_id")
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false)
	private int capacity;

	protected Stadium() {

	}

	public Stadium(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}
}
