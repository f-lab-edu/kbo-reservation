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
	private int capacity; // 총 수용 인원

	private long allocatedSeatCount = 0; // 할당된 누적 블록 좌석 수

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

	public long getAllocatedSeatCount() {
		return allocatedSeatCount;
	}

	public void allocateSeatCount(long seatCount) {
		if ((allocatedSeatCount + seatCount) > capacity) {
			throw new IllegalStateException("Stadium total capacity exceeded.");
		}
		allocatedSeatCount += seatCount;
	}
}
