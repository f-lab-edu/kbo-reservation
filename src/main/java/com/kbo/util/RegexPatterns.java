package com.kbo.util;

import java.util.regex.Pattern;

public class RegexPatterns {
	public static final Pattern ALPHANUMERIC_8 = Pattern.compile("^[A-Za-z0-9]{8}$");

	// 유틸 클래스이므로 생성자 private 처리
	private RegexPatterns() {}
}
