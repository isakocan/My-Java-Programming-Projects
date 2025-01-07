package uygulama3;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
	private ArrayList<Doctor> medicalPersonelList;

	public Hospital() {
		medicalPersonelList = new ArrayList<Doctor>();
	}
	
	public void addMedicalPersonel(Doctor personel) {
		medicalPersonelList.add(personel);
	}
	
	public void assignPatientToDoctor(Doctor doctor, Patient patient, Treatment treatment) {
		double dailyFee = 100;
		double totalFee;
		try {
			doctor.performSurgery();
			for (int i = 0; i < doctor.getSurgeries(); i++) {
				System.out.println(doctor.getName()+" performed succesful surgery on "+patient.getName());
				System.out.println("Prescribed Treatment: "+treatment);
				int durationOfStay = (int) (Math.random() * 10) + 1;
				Room room = new Room(durationOfStay);
				System.out.println("Allocated room: "+room);
				
				room.addTreatment(treatment);
				System.out.println("Daily fee for "+patient.getName()+": $"+dailyFee);
				totalFee = durationOfStay * dailyFee;
				System.out.println("Total fee for"+patient.getName()+": $"+totalFee);
				System.out.println("***** Treatment End *****");
			}
		} catch (SurgeryUnsuccessfulExpection e) {
			System.out.println(e.getMessage());
			System.out.println(patient.getName()+" did not survive the surgery.");
			totalFee = 0;
			System.out.println("Total fee for "+patient.getName()+": $"+totalFee);
			System.out.println("***** Treatment End *****");
		}
	}
	
	public static <Z> void showListDoctor(List <Z> doctors) {
		for (Z z : doctors) {
			System.out.println(z);
		}
	}
}
