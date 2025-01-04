package clinicReservationSystem;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	private LinkedList<Rendezvous> sessions;
	private int maxPatientPerDay;
	private Doctor doctor;

	public Schedule(int maxPatientPerDay) {
		this.sessions = new LinkedList<Rendezvous>();
		this.maxPatientPerDay = maxPatientPerDay;
	}

	public LinkedList<Rendezvous> getSessions() {
		return sessions;
	}

	public int getMaxPatientPerDay() {
		return maxPatientPerDay;
	}

	public void setMaxPatientPerDay(int maxPatientPerDay) {
		this.maxPatientPerDay = maxPatientPerDay;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public boolean addRendezvous(Patient patient, Date desiredDate) {

		Calendar desiredCal = Calendar.getInstance();
		desiredCal.setTime(desiredDate);

		Calendar rendezvousCal = Calendar.getInstance();

		int currentRendezvousCount = 0;
		for (Rendezvous rendezvous : sessions) {
			rendezvousCal.setTime(rendezvous.getDateTime());
			if (desiredCal.get(Calendar.YEAR) == rendezvousCal.get(Calendar.YEAR)
					&& desiredCal.get(Calendar.DAY_OF_YEAR) == rendezvousCal.get(Calendar.DAY_OF_YEAR)) {
				currentRendezvousCount++;
			}
		}

		if (currentRendezvousCount < maxPatientPerDay) {
			sessions.add(new Rendezvous(patient, desiredDate, doctor));
			return true;
		} else
			return false;
	}

}