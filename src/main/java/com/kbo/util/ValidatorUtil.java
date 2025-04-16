package com.kbo.util;

import static com.kbo.util.RegexPatterns.*;

public class ValidatorUtil {
	public static boolean isAlphanumeric8(String input) {
		return input != null && ALPHANUMERIC_8.matcher(input).matches();
	}
}
