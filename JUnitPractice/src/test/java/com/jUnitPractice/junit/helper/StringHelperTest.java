package com.jUnitPractice.junit.helper;

import static org.junit.Assert.*;

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
}
