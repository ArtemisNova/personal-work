package com.jUnitPractice.junit.helper;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import junit.framework.TestCase;

/* 
 * A version of StringHelperTest which utilises the power of parameterised test
 */
@RunWith(Parameterized.class)
public class StringHelperParameterisedTest extends TestCase {
	
	private StringHelper helper;
	private String input, expected;
	
	public StringHelperParameterisedTest(String input, String expected) {
		this.input = input;
		this.expected  = expected;
		helper = new StringHelper();
	}
	
	@Parameters
	public static Collection<String[]> testConditions() {
		String testOutputs[][] = {
			{"AACD","CD"},
			{"ACD","CD"},
			{"CDEF", "CDEF"},
			{"CDAA", "CDAA"} };
		return Arrays.asList(testOutputs);
	}

	@Test
	public void testTruncateAInFirst2Positions() {
		assertEquals(expected, helper.truncateAInFirst2Positions(input));
	}
}
