package clinicReservationSystem;

public class Doctor extends Person {

	private static final long serialVersionUID = 1L;

	private final int diplomaId;
	private Schedule schedule;

	public Doctor(String name, long nationalId, int diplomaId) {
		super(name, nationalId);
		this.diplomaId = diplomaId;
		this.schedule = new Schedule(10);
		this.schedule.setDoctor(this);
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public int getDiplomaId() {
		return diplomaId;
	}

	@Override
	public String toString() {
		return "Doktor ismi: " + getName() + ", Diploma id: " + diplomaId + ", Kimlik no: " + getNationalId();
	}

}