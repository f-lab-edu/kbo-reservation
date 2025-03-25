package com.kbo.queue.controller.response;

import java.security.SecureRandom;
import java.util.Random;

public record QueueSession(
	String token,
	long gameId,
	long userId
) {
	public static QueueSession create(long gameId, long userId) {
		return new QueueSession(generateToken(gameId, userId), gameId, userId);
	}

	private static String generateToken(long gameId, long userId) {
		String randomString = generateRandomString(8);
		long nanoTime = System.nanoTime();
		return randomString + "-" + gameId + "-" + userId + "-" + nanoTime;
	}

	private static String generateRandomString(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		SecureRandom secureRandom = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(secureRandom.nextInt(chars.length())));
		}
		return sb.toString();
	}
}
