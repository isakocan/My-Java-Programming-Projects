package isakocan;

public class PassengerCar extends Car{

	public PassengerCar(int id, String brand, String model, double dailyRentalPrice) {
		super(id, brand, model, dailyRentalPrice);
	}

	@Override
	public double calculateFuelCosts(int distance, double price) {
		return price*0.05*distance;
	}
	
	
	
	
	
	
}
