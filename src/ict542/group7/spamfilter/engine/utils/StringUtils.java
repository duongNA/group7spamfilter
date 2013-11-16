package ict542.group7.spamfilter.engine.utils;

public class StringUtils {
	public static boolean containsOnlyDigits(String s) {
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		
		return true;
	}
}
