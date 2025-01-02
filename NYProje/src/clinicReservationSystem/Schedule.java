package clinicReservationSystem;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;

public class Schedule implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private LinkedList<Rendezvous> sessions;
    private int maxPatientPerDay;

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

    public boolean addRendezvous(Patient patient, Calendar desiredDate) {

        int currentRendezvousCount = 0;
         for(Rendezvous rendezvous : sessions){
             Calendar rendezvousDate = rendezvous.getDateTime();
             if(rendezvousDate.get(Calendar.YEAR) == desiredDate.get(Calendar.YEAR) &&
                rendezvousDate.get(Calendar.DAY_OF_YEAR) == desiredDate.get(Calendar.DAY_OF_YEAR)){
                 currentRendezvousCount++;
             }
        }

        if (currentRendezvousCount < maxPatientPerDay) {
            sessions.add(new Rendezvous(patient, desiredDate));
            return true;
        }
        else
        	return false;
    }

    
}