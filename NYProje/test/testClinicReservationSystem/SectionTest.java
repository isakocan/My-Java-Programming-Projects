package testClinicReservationSystem;

import clinicReservationSystem.Doctor;
import clinicReservationSystem.DuplicateInfoException;
import clinicReservationSystem.Section;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SectionTest {
	@Test
	public void testSectionCreation() {
		int id = 101;
		String name = "Kardiyoloji";
		int maxPatientPerDay = 2;
		Section section = new Section(id, name, maxPatientPerDay);
		assertEquals(id, section.getId());
		assertEquals(name, section.getName());
		assertEquals(maxPatientPerDay, section.getMaxPatientPerDay());
	}

	@Test
	public void testGetDoctorByDiplomaId() throws DuplicateInfoException {
		int id = 101;
		String name = "Kardiyoloji";
		int maxPatientPerDay = 2;
		Section section = new Section(id, name, maxPatientPerDay);
		Doctor doctor1 = new Doctor("Ayşe Kaya", 11223344556L, 777);
		Doctor doctor2 = new Doctor("Ali Veli", 33445566778L, 888);
		section.addDoctor(doctor1);
		section.addDoctor(doctor2);

		assertEquals(doctor1, section.getDoctor(777));
		assertEquals(doctor2, section.getDoctor(888));
		assertNull(section.getDoctor(999));
	}

	@Test
	public void testAddDoctor() throws DuplicateInfoException {
		int id = 101;
		String name = "Kardiyoloji";
		int maxPatientPerDay = 2;
		Section section = new Section(id, name, maxPatientPerDay);
		Doctor doctor = new Doctor("Ayşe Kaya", 11223344556L, 777);
		section.addDoctor(doctor);
		assertTrue(section.listDoctors().contains(doctor));
	}

	@Test(expected = DuplicateInfoException.class)
	public void testAddDuplicateDoctor() throws DuplicateInfoException {
		int id = 101;
		String name = "Kardiyoloji";
		int maxPatientPerDay = 2;
		Section section = new Section(id, name, maxPatientPerDay);
		Doctor doctor1 = new Doctor("Ayşe Kaya", 11223344556L, 777);
		Doctor doctor2 = new Doctor("Ali Veli", 33445566778L, 777);
		section.addDoctor(doctor1);
		section.addDoctor(doctor2);

	}

	@Test
	public void testSectionSetName() {
		int id = 101;
		String name = "Kardiyoloji";
		int maxPatientPerDay = 2;
		Section section = new Section(id, name, maxPatientPerDay);
		String newName = "Dahiliye";
		section.setName(newName);
		assertEquals(newName, section.getName());
	}

	@Test
	public void testSectionNotNull() {
		int id = 101;
		String name = "Kardiyoloji";
		int maxPatientPerDay = 2;
		Section section = new Section(id, name, maxPatientPerDay);
		assertNotNull(section.listDoctors());
		assertNotNull(section.toString());
	}
}