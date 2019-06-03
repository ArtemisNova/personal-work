package com.jUnitPractice.junit.helper;

import static org.junit.Assert.*;

import org.junit.*;

// quick test class messing around with before after in junit

public class QuickBeforeAfterTest {
	
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Done before ANY test!");
	}
	
	@AfterClass
	public static void AfterClass() {
		System.out.println("Done after EVERY test!");
	}
	
	@Before
	public void setup() {
		System.out.println("Done before test!");
	}
	
	@After
	public void teardown() { 
		System.out.println("Done after test!");
	}

	@Test
	public void test1() {
		System.out.println("test1 executed");
	}
	
	@Test
	public void test2() {
		System.out.println("test2 executed");
	}
}
