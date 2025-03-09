package com.kbo.queue.constant;

import java.util.Arrays;

public enum QueueKeyPrefix {
	QUEUE("queue:"),
	TTL("queue:ttl:");

	private final String prefix;

	QueueKeyPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String format(Object... values) {
		return prefix + String.join(":", Arrays.stream(values)
			.map(Object::toString)
			.toArray(String[]::new));
	}
}
