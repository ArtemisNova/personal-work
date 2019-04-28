package main;

// Represents a rental of a vehicle
public class Rental {

  public Rental(Vehicle vehicle, int daysRented) {
    _vehicle = vehicle;
    _daysRented = daysRented;
  }

  public double getRentalAmount() {
	return _vehicle.getRentalCost(_daysRented);
}
  
  public int getFrequentRenterPoints() {
	  int points = 1 + _vehicle.getBonusPoints(_daysRented);
	  
	  return points;
  }
  
  public String toString() {
	  String result = new String();
	  result += "\t" + _vehicle.getTitle() + "\t" + String.valueOf(getRentalAmount()) + "\n";
	  return result;
  }

  private Vehicle _vehicle;
  private int _daysRented;
}