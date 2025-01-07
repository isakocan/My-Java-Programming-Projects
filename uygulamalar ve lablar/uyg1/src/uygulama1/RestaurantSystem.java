package uygulama1;

public class RestaurantSystem {

	public static void main(String[] args) {
		Customer cst1 = new Customer("İsa", "543-745-4160");
		Table table1 = new Table(1, 4);
		Reservation res1 = new Reservation(cst1, table1, "11.00");
		Reservation res2 = new Reservation(cst1, table1, "12.00");
		Reservation res3 = new Reservation(cst1, table1, "13.00");
		Reservation res4 = new Reservation(cst1, table1, "14.00");
		Reservation res5 = new Reservation(cst1, table1, "15.00");
		
		cst1.addReservation(res1);
		
		table1.addReservation(res1);
		table1.addReservation(res2);
		table1.addReservation(res3);
		table1.addReservation(res4);
		table1.addReservation(res5);
		
		table1.removeReservation(res3);
		table1.addReservation(res5);
		
		System.out.println();
		cst1.toString();
		cst1.listReservation();
		table1.getReservations();
	}

}
