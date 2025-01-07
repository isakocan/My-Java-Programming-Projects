package isakocan;

import java.util.ArrayList;

public class CustomerCompany {
	private String name;
	private ArrayList<Car> rentedCars;
	
	public CustomerCompany(String name) {
		this.name = name;
		rentedCars = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public ArrayList<Car> getRentedCars() {
		return rentedCars;
	}

	public void rentCar(Car car) {
		rentedCars.add(car);
	}
	
	public int getRentalCount() {
		return rentedCars.size();
	}
	
	
	
	
	
	
}
