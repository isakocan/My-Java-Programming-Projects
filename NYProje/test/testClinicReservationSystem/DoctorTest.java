package testClinicReservationSystem;

import clinicReservationSystem.Doctor;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DoctorTest {
	@Test
	public void testDoctorCreation() {
		String name = "Mehmet Demir";
		long nationalId = 99887766554L;
		int diplomaId = 888;
		Doctor doctor = new Doctor(name, nationalId, diplomaId);
		assertEquals(name, doctor.getName());
		assertEquals(nationalId, doctor.getNationalId());
		assertEquals(diplomaId, doctor.getDiplomaId());
	}

	@Test
	public void testDoctorToString() {
		String name = "Mehmet Demir";
		long nationalId = 99887766554L;
		int diplomaId = 888;
		Doctor doctor = new Doctor(name, nationalId, diplomaId);
		String expectedToString = "Doktor ismi: " + name + ", Diploma id: " + diplomaId;
		assertEquals(expectedToString, doctor.toString());
	}

	@Test
	public void testDoctorNotNull() {
		String name = "Mehmet Demir";
		long nationalId = 99887766554L;
		int diplomaId = 888;
		Doctor doctor = new Doctor(name, nationalId, diplomaId);
		assertNotNull(doctor);
		assertNotNull(doctor.getName());
		assertNotNull(doctor.getSchedule());
		assertNotNull(doctor.toString());
	}
}