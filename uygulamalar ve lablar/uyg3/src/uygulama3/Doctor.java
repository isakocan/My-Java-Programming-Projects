package uygulama3;

import java.util.ArrayList;


public class Doctor implements IMedicalPersonel, ISurgeon{

	private static ArrayList<Doctor> doctorList = new ArrayList<Doctor>();
	private String name;
	private double experience;
	private int surgeries;
	
	
	
	public Doctor(String name) {
		this.name = name;
		this.experience = 0.0;
		this.surgeries = 2;
		doctorList.add(this);
	}

	
	public static ArrayList<Doctor> getDoctorList(){
		return Doctor.doctorList;
	}
	
	
	
	public int getSurgeries() {
		return surgeries;
	}


	@Override
	public void performSurgery() throws SurgeryUnsuccessfulExpection{
		for (int i = 0; i < surgeries; i++) {
			boolean surgerySuccessful = Math.random() < 0.8;
			if(surgerySuccessful)
				experience += 2.5;
			else {
				experience = 0.0;
				throw new SurgeryUnsuccessfulExpection("The surgery by "+name+" was unsuccessful!");
			}
		}
		
	}

	@Override
	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return "Doctor: "+ name + ", experience: " + experience + " surgeries.";
	}
	
	
}
