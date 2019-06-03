package com.jUnitPractice.junit.helper;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class ArraysCompareTest {

	@Test
	public void testArraySort_Success() {
		int[] nums = {22,1,65,12};
		int[] expected = {1,12,22,65};
		Arrays.sort(nums);
		assertArrayEquals(expected, nums);
	}

	@Test(expected=NullPointerException.class)
	public void testArraySort_NullArray() {
		int[] nums = null;
		Arrays.sort(nums);
	}
	
	// testing performance
	@Test(timeout=100)
	public void testSort_Performance() {
		int array[] = {12,23,4};
		for(int i = 0; i <= 1000000; i++) {
			array[0] = 1;
			Arrays.parallelSort(array);
		}
	}
}
