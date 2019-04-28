package part2;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;

import org.junit.jupiter.api.Test;

class TestTriangle {

	@Test
	public void testTriangleCreation() {
		Triangle triangle = new Triangle(1,2,3);
		
		assertEquals("Triangle sizes on creation are not correct", 1, triangle.sides[0]);
		assertEquals("Triangle sizes on creation are not correct", 2, triangle.sides[1]);
		assertEquals("Triangle sizes on creation are not correct", 3, triangle.sides[2]);
	}
	
	@Test
	public void testTriangleEquilateral() {
		Triangle triangle = new Triangle(2,2,2);
		assertTrue("Triangle is not Equilateral", "Equilateral".equals(triangle.typeOf()));
	}
	
	@Test
	public void testTriangleIsoceles() {
		Triangle triangle1 = new Triangle(2,2,3);
		Triangle triangle2 = new Triangle(2,3,2);
		Triangle triangle3 = new Triangle(3,2,2);
		
		assertTrue("Triangle is not Isoceles", triangle1.typeOf().equals("Isoceles"));
		assertTrue("Triangle is not Isoceles", triangle2.typeOf().equals("Isoceles"));
		assertTrue("Triangle is not Isoceles", triangle3.typeOf().equals("Isoceles"));
	}
	
	@Test
	public void testTriangleScalene() {
		Triangle triangle = new Triangle(3,4,5);
		
		assertTrue("Triangle is Scalene but not recognised as such", triangle.typeOf().equals("Scalene"));
	}
	
	@Test
	public void testInvalidTriangle() {
		Triangle triangle1 = new Triangle(1,2,20);
		Triangle triangle2 = new Triangle(20,1,2);
		Triangle triangle3 = new Triangle(1,20,2);
		
		assertTrue("Triangle is Invalid, but not recognised as such", triangle1.typeOf().equals("Invalid"));
		assertTrue("Triangle is Invalid, but not recognised as such", triangle2.typeOf().equals("Invalid"));
		assertTrue("Triangle is Invalid, but not recognised as such", triangle3.typeOf().equals("Invalid"));
	}

}
