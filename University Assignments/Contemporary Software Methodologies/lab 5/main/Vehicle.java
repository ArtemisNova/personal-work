package main;

public abstract interface Vehicle {
  public String getTitle();
  public abstract double getRentalCost(int numDaysRented);
  public default int getBonusPoints(int numDaysRented) {
	  return 0;
  }
}
