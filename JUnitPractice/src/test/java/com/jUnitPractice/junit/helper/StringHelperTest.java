package com.jUnitPractice.junit.helper;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringHelperTest {

	@Test
	public void testTruncateAInFirst2Positions() {
		StringHelper helper = new StringHelper();
		String result1 = helper.truncateAInFirst2Positions("AACD");
		String result2 = helper.truncateAInFirst2Positions("ACD");
		assertEquals("CD", result1);
		assertEquals("CD", result2);
	}

}
