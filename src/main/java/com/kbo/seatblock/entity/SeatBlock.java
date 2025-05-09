package com.kbo.seatblock.entity;

import com.kbo.seatgrade.entity.SeatGrade;
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
@Table(name = "seat_block")
public class SeatBlock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seat_block_id")
	private Long id;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false)
	private int seatCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stadium_id")
	private Stadium stadium;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_grade_id", nullable = false)
	private SeatGrade seatGrade;

	protected SeatBlock() {

	}

	public SeatBlock(String name, int seatCount, Stadium stadium, SeatGrade seatGrade) {
		this.name = name;
		this.seatCount = seatCount;
		setStadium(stadium);
		setSeatGrade(seatGrade);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public Stadium getStadium() {
		return stadium;
	}

	public SeatGrade getSeatGrade() {
		return seatGrade;
	}

	private void setStadium(Stadium stadium) {
		this.stadium = stadium;
		stadium.getSeatBlocks().add(this);
		stadium.allocateSeatCount(seatCount);
	}

	private void setSeatGrade(SeatGrade seatGrade) {
		this.seatGrade = seatGrade;
	}
}
