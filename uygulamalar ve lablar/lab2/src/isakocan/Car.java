package isakocan;

public abstract class Car {
	protected int id;
	protected String brand;
	protected String model;
	protected double dailyRentalPrice;
	
	
	public Car(int id, String brand, String model, double dailyRentalPrice) {
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.dailyRentalPrice = dailyRentalPrice;
	}
	
	
	public abstract double calculateFuelCosts(int distance, double price);


	public double getDailyRentalPrice() {
		return dailyRentalPrice;
	}
	
	
	
}
