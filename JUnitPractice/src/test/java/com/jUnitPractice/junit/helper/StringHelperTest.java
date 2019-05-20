package com.jUnitPractice.junit.helper;

import org.junit.Test;

import junit.framework.TestCase;

public class StringHelperTest extends TestCase {
	
	private StringHelper helper;
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		helper = new StringHelper();
	}
	
	@Test
	public void testTruncateAInFirst2Position_EmptyString() {
		assertEquals("", helper.truncateAInFirst2Positions(""));
	}
	
	@Test
	public void testTruncateAInFirst2Positions_First2Pos() {
		
		assertEquals("CD", helper.truncateAInFirst2Positions("AACD"));
	}

	@Test
	public void testTruncateAInFirst2Positions_First1Pos() {
		assertEquals("CD", helper.truncateAInFirst2Positions("ACD"));
	}
	
	@Test
	public void testTruncateAInFirst2Positions_NoAPresent() {
		assertEquals("CDEF", helper.truncateAInFirst2Positions("CDEF"));
	}
	
	@Test
	public void testTruncateAInFirst2Positions_AInLaterPositions() {
		assertEquals("CDAA", helper.truncateAInFirst2Positions("CDAA"));
	}
	
	@Test
	public void testAreFirstAndLastTwoCharactersTheSame_EmptyString() {
		assertFalse(helper.areFirstAndLastTwoCharactersTheSame(""));
	}
	
	@Test
	public void testAreFirstAndLastTwoCharactersTheSame_NegativeScenario() {
		assertFalse(helper.areFirstAndLastTwoCharactersTheSame("ABCD"));
	}
	
	@Test
	public void testAreFirstAndLastTwoCharactersTheSame_PositiveScenario() {
		assertTrue(helper.areFirstAndLastTwoCharactersTheSame("ABAB"));
	}
	
	@Test
	public void testAreFirstAndLastTwoCharactersTheSame_StringLength2() {
		assertTrue(helper.areFirstAndLastTwoCharactersTheSame("AB"));
	}
	
	@Test
	public void testAreFirstAndLastTwoCharactersTheSame_StringLength1() {
		assertFalse(helper.areFirstAndLastTwoCharactersTheSame("A"));
	}
}
