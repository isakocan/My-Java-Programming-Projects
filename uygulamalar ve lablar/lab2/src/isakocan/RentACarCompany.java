package isakocan;

import java.util.ArrayList;

public class RentACarCompany {
	private String name;	
	private ArrayList<Car> carList;
	private ArrayList<CustomerCompany> customerList;
	
	
	public RentACarCompany(String name) {
		this.name = name;
		customerList = new ArrayList<>();
		carList = new ArrayList<>();
	}


	public void rentCar(CustomerCompany customer, Car car) {
		if(!customerList.contains(customer)) {
			customerList.add(customer);
		}
		customer.rentCar(car);
	}


	public ArrayList<Car> getCarList() {
		return carList;
	}

	public String mostFrequentCustomer() {
		int max = 0;
		CustomerCompany mostFrequentCustomer = null;
		for (CustomerCompany customer : customerList) {
			if(customer.getRentalCount() > max) {
				mostFrequentCustomer = customer;
				max = customer.getRentalCount();
			}
		}
		return mostFrequentCustomer.getName();
	}
	
	
	public void calculateTotalCosts(int rentalDays) {
		double totalCost = 0;
		for (CustomerCompany customer : customerList) {
			for (Car car : customer.getRentedCars()) {
				totalCost += car.getDailyRentalPrice()*rentalDays;
			}
			System.out.println(customer.getName()+"'s total cost for "+rentalDays+" days: "+totalCost+" TL");
			totalCost = 0;
		}		
	}


	public String getName() {
		return name;
	}

	
}
