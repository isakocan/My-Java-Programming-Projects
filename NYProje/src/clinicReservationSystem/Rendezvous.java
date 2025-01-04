package clinicReservationSystem;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Rendezvous implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private Date dateTime;
    private Patient patient;
    private Doctor doctor;

    public Rendezvous(Patient patient, Date dateTime, Doctor doctor) {
        this.patient = patient;
        this.dateTime = dateTime;
        this.doctor = doctor;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(dateTime);
        return "Randevu tarihi: " + formattedDate + ", hasta adı: " + patient.getName() + ", doktor adı: " + doctor.getName();
    }

	
    
}