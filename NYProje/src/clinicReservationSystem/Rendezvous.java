package clinicReservationSystem;

import java.io.Serializable;
import java.util.Calendar;

public class Rendezvous implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private Calendar dateTime;
    private Patient patient;

    public Rendezvous(Patient patient, Calendar dateTime) {
        this.patient = patient;
        this.dateTime = dateTime;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    
}