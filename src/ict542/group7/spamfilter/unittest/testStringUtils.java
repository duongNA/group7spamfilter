package ict542.group7.spamfilter.unittest;

import ict542.group7.spamfilter.engine.utils.StringUtils;

import org.junit.Assert;
import org.junit.Test;

public class testStringUtils {

	@Test
	public void testContainsOnlyDigits() {
		String s = "123456789";
		Assert.assertTrue(StringUtils.containsOnlyDigits(s));
		
		s = "123456789a";
		Assert.assertFalse(StringUtils.containsOnlyDigits(s));
	}
}
