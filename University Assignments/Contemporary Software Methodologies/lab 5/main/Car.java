package main;

public class Car implements Vehicle {
	
	public Car(String model) {
		_model = model;
	}
	
	@Override
	public String getTitle() {
		return _model;
	}
	
	@Override
	public double getRentalCost(int numDaysRented) {
		double amount = 2;
        if (numDaysRented > 2)
          amount += (numDaysRented - 2) * 1.5;
        return amount;
	}
	
	private String _model;

}
