package isakocan;

public class Applicant {

	private String name;
	private String passportNumber;
	private boolean hasWorkPermit;

	public Applicant(String name, String passportNumber, boolean hasWorkPermit) {
		this.name = name;
		this.passportNumber = passportNumber;
		this.hasWorkPermit = hasWorkPermit;
	}

	public String getName() {
		return name;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public boolean isHasWorkPermit() {
		return hasWorkPermit;
	}

	@Override
	public String toString() {
		String tmp;
		if (hasWorkPermit) {
			tmp = "Var";
		} else {
			tmp = "Yok";
		}
		return "Başvuru Sahibi: " + name + "\nPasaport No: " + passportNumber + "\nÇalışma izni: " + tmp;
	}

}
