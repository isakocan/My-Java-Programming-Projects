package isakocan;

public class CommercialCar extends Car{

	
	public CommercialCar(int id, String brand, String model, double dailyRentalPrice) {
		super(id, brand, model, dailyRentalPrice);
	}

	@Override
	public double calculateFuelCosts(int distance, double price) {		
		return price*0.1*distance;
	}
	
	
	
	
	
	
}
