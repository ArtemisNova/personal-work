package main;

import org.junit.tests.AllTestsTest.All;

public class AllTerrainVehicle implements Vehicle{
	
	public AllTerrainVehicle(String model) {
		_model = model;
	}
	
	@Override
	public String getTitle() {
		return _model;
	}
	
	@Override
	public double getRentalCost(int numDaysRented) {
		return numDaysRented * 3;
	}
	
	@Override
	public int getBonusPoints(int numDaysRented) {
		if(numDaysRented > 1) return 1;
		else return 0;
	}
	
	private String _model;
}
