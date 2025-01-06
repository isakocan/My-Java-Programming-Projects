package clinicReservationSystem;

import java.io.Serializable;
import java.util.LinkedList;

public class Section implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int id;
	private String name;
	private LinkedList<Doctor> doctors;
	private int maxPatientPerDay;

	public Section(int id, String name, int maxPatientPerDay) {
		this.id = id;
		this.name = name;
		this.doctors = new LinkedList<Doctor>();
		this.maxPatientPerDay = maxPatientPerDay;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Doctor> listDoctors() {
		return doctors;
	}

	public Doctor getDoctor(int diplomaId) {
		for (Doctor doctor : doctors) {
			if (doctor.getDiplomaId() == diplomaId) {
				return doctor;
			}
		}
		return null;
	}

	public void addDoctor(Doctor doctor) throws DuplicateInfoException{
		for (Doctor doc : doctors) {
			if (doc.getDiplomaId() == doctor.getDiplomaId()) {
				throw new DuplicateInfoException("Bu diploma id'ye sahip bir doktor zaten mevcut.");
			}
			else if(doc.getNationalId() == doctor.getNationalId()) {
				throw new DuplicateInfoException("Bu kimlik no'ya sahip bir doktor zaten mevcut.");
			}
		}

		doctor.getSchedule().setMaxPatientPerDay(maxPatientPerDay);
		this.doctors.add(doctor);
	}

	@Override
	public String toString() {
		return "Bölüm adı: " + name + ", id: " + id;
	}
}