package uygulama1;

public class Reservation {
	
	
	private Customer customer;
	private String reservationTime;
	
	
	public Reservation(Customer customer, Table table, String reservationTime) {
		this.customer = customer;
		this.reservationTime = reservationTime;
	}


	public Customer getCustomer() {
		return customer;
	}


	public String getReservationTime() {
		return reservationTime;
	}
	
	@Override
	public String toString() {
		return "Customer name: " + customer.getName() + ", Phone number: " + customer.getPhoneNumber() + ", Reservation time: " + reservationTime;
	}
	
	
	
	
	
	
	
	
	
}
