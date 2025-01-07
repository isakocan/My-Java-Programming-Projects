package uygulama3;

import java.util.ArrayList;

public class Room {
	private static int NextRoomNumber = 1;
	private int roomNumber;
	private int durationOfStay;
	private ArrayList<Treatment> treatments;
	
	public Room(int durationOfStay) {
		this.roomNumber = NextRoomNumber++;
		this.durationOfStay = durationOfStay;
		this.treatments = new ArrayList<Treatment>();
	}

	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void addTreatment (Treatment treatment) {
		treatments.add(treatment);
	}

	@Override
	public String toString() {
		return "Room: " + roomNumber + ", durationOfStay: " + durationOfStay + "days";
	}
	
	
}
