package uygulama1;

import java.util.ArrayList;

public class Table {
	
	
	private int tableNumber;
	private int seats;
	private boolean avaible;
	private ArrayList<Reservation> reservations;
	private int currentReservations;
	private static final int MAX_RESERVATIONS = 4;
	
	public Table(int tableNumber, int seats) {
		this.tableNumber = tableNumber;
		this.seats = seats;
		this.avaible = true;
		this.reservations = new ArrayList<Reservation>();
		this.currentReservations = 0;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public void getReservations() {
		if(reservations.isEmpty()) {
			System.out.println("There is no reservation!");
		}
		else {
			System.out.println("Reservatins for this table: " + tableNumber);
			for (Reservation reservation : reservations) {
				System.out.println(reservation.toString());
			}
		}
		
		
	}

	public void setAvaible(boolean avaible) {
		this.avaible = avaible;
	}
	
	public boolean getAvaible() {
		return avaible;
	}
	
	public void addReservation(Reservation reservation) {
		if(currentReservations < MAX_RESERVATIONS) {
			reservations.add(reservation);
			currentReservations ++;
			
			if(currentReservations == MAX_RESERVATIONS) {
				avaible = false;
			}
			
			System.out.println("The reservation is added. Current reservations are this table: " + currentReservations);
			
		}
		else {
			System.out.println("The reservation is not added because of maximum reservation.");
		}
		
		
	}
	
	public void removeReservation(Reservation reservation) {	
		if(reservations.remove(reservation)) {
			currentReservations --;
			System.out.println("The reservation is removed. Current reservations are this table: "+currentReservations);
			avaible = true;
		}
		else {
			System.out.println("Reservation is not found!");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
