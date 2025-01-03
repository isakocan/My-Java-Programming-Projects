package clinicReservationSystem;


public class Doctor extends Person {
    
	private static final long serialVersionUID = 1L;
	
	private final int diplomaId;
	private Schedule schedule;

    public Doctor(String name, long nationalId, int diplomaId, int maxPatientPerDay) {
        super(name, nationalId);
        this.diplomaId = diplomaId;
        this.schedule = new Schedule(maxPatientPerDay);
    }

    public Schedule getSchedule() {
		return schedule;
	}
    
    
    public int getDiplomaId() {
        return diplomaId;
    }

    @Override
    public String toString() {
        return "Doctor name: " + getName() + ", National Id: " + getNationalId() +
                ", Diploma Id:" + diplomaId ;
    }

}