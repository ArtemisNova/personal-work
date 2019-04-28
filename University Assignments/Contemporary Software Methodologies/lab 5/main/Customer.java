package main;

import java.util.ArrayList;
import java.util.List;

public class Customer {

	public Customer(String name) {
		_name = name;
	}

	public void addRental(Rental arg) {
		_rentals.add(arg);
	}

	public String getName() {
		return _name;
	}

	private double totalRentalCost() {
		double amount = 0.0;
		for(Rental current: _rentals) amount += current.getRentalAmount();
		return amount;
	}

	private int totalFrequentRenterPoints() {
		int points = 0;
		for(Rental current: _rentals) points += current.getFrequentRenterPoints();
		return points;
	}

	public String statement() {
		String result = "Rental Record for " + getName() + "\n";
		for(Rental each: _rentals) result += each.toString();
		result += "Amount owed is " + String.valueOf(totalRentalCost()) + "\n";
		result += "You earned " + String.valueOf(totalFrequentRenterPoints()) + " frequent renter points";
		return result;
	}

	private String _name;
	private List<Rental> _rentals = new ArrayList<Rental>();
}
