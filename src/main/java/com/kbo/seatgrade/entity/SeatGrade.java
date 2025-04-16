package com.kbo.seatgrade.entity;

import com.kbo.seatgrade.constant.SeatSide;
import com.kbo.stadium.entity.Stadium;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "seat_grade")
public class SeatGrade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seat_grade_id")
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private SeatSide seatSide;

	private int price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stadium_id", nullable = false)
	private Stadium stadium;

	protected SeatGrade() {

	}

	public SeatGrade(String name, SeatSide seatSide, int price, Stadium stadium) {
		this.name = name;
		this.seatSide = seatSide;
		this.price = price;
		this.stadium = stadium;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public SeatSide getSeatSide() {
		return seatSide;
	}

	public int getPrice() {
		return price;
	}

	public Stadium getStadium() {
		return stadium;
	}

	public String getDisplayName() {
		return String.format("%s %s", seatSide.getDisplayName(), name);
	}
}
