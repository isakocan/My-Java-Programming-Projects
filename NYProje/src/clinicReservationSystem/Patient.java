package clinicReservationSystem;

import java.io.Serializable;

public class Patient extends Person implements Serializable {
    
	private static final long serialVersionUID = 1L;

	public Patient(String name, long nationalId) {
        super(name, nationalId);
    }

    @Override
    public String toString() {
        return "Patient name: " + getName() + ", National Id: " + getNationalId();
    }
}
