package main;

public class Motorbike implements Vehicle{
	
	public Motorbike(String model) {
		_model = model;
	}

	@Override
	public String getTitle() {
		return _model;
	}

	@Override
	public double getRentalCost(int numDaysRented) {
		double amount = 1.5;
		if(numDaysRented > 3)
			amount += (numDaysRented - 3) * 1.5;
		return amount;
	}

	private String _model;
}
