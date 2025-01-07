package testClinicReservationSystem;

import clinicReservationSystem.Patient;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PatientTest {

	@Test
	public void testPatientCreation() {
		String name = "Elif Kaya";
		long nationalId = 98765432109L;
		Patient patient = new Patient(name, nationalId);
		assertEquals(name, patient.getName());
		assertEquals(nationalId, patient.getNationalId());
	}

	@Test
	public void testPatientToString() {
		String name = "Elif Kaya";
		long nationalId = 98765432109L;
		Patient patient = new Patient(name, nationalId);
		String expectedToString = "Hasta adı: " + name + ", Kimlik no: " + nationalId;
		assertEquals(expectedToString, patient.toString());
	}

	@Test
	public void testPatientNotNull() {
		String name = "Elif Kaya";
		long nationalId = 98765432109L;
		Patient patient = new Patient(name, nationalId);
		assertNotNull(patient);
		assertNotNull(patient.getName());
		assertNotNull(patient.toString());
	}
}