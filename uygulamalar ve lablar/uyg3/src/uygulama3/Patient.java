package uygulama3;

import java.util.LinkedList;

public class Patient implements IMedicalPersonel{
	private String name;
	private LinkedList<Treatment> treatments;
	
	public Patient(String name) {
		this.name = name;
		this.treatments = new LinkedList<Treatment>();
	}

	@Override
	public String getName() {
		return name;
	}

	public LinkedList<Treatment> getTreatments() {
		return treatments;
	}

	
	public void addTreatments(Treatment treatment) {
		treatments.add(treatment);
	}
	
	@Override
	public String toString() {
		return "Patient:  " + name + ", treatments: " + treatments;
	}
	
	
}
