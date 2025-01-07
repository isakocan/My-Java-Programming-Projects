package isakocan;

public class TransportCar extends Car{
	
	
	private int passengerCapacity;

	public TransportCar(int id, String brand, String model, double dailyRentalPrice, int passengerCapacity) {
		super(id, brand, model, dailyRentalPrice);
		this.passengerCapacity = passengerCapacity;
	}

	@Override
	public double calculateFuelCosts(int distance, double price) {
		return price*0.2*distance;
	}

	public int getPassengerCapacity() {
		return passengerCapacity;
	}
	
	
	
	
	
	
}
