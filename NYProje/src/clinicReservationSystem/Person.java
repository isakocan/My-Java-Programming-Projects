package clinicReservationSystem;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private final long nationalId;

	public Person(String name, long nationalId) {
		this.name = name;
		this.nationalId = nationalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNationalId() {
		return nationalId;
	}

	@Override
	public String toString() {
		return "Name: " + name + ", Id: " + nationalId;
	}
}