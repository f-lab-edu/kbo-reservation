package com.kbo.queue.constant;

public enum SessionKey {
	SESSION_TOKEN_ATTRIBUTE("sessionToken"),
	SESSION_TOKEN_HEADER("X-Session-Token");

	private final String key;

	SessionKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
