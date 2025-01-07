package testClinicReservationSystem;

import clinicReservationSystem.DuplicateInfoException;
import clinicReservationSystem.Hospital;
import clinicReservationSystem.Section;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class HospitalTest {
	@Test
	public void testHospitalCreation() {
		int id = 1;
		String name = "Merkez Hastanesi";
		Hospital hospital = new Hospital(id, name);
		assertEquals(id, hospital.getId());
		assertEquals(name, hospital.getName());
	}

	@Test
	public void testGetSection() throws DuplicateInfoException {
		Hospital hospital = new Hospital(1, "Merkez Hastanesi");
		Section section1 = new Section(101, "Kardiyoloji", 2);
		Section section2 = new Section(102, "Dahiliye", 3);
		hospital.addSection(section1);
		hospital.addSection(section2);

		assertEquals(section1, hospital.getSection(101));
		assertEquals(section2, hospital.getSection(102));
		assertNull(hospital.getSection(103));
	}
	
	@Test
	public void testAddSection() throws DuplicateInfoException {
		int id = 1;
		String name = "Merkez Hastanesi";
		Hospital hospital = new Hospital(id, name);
		Section section = new Section(101, "Kardiyoloji", 2);
		hospital.addSection(section);
		assertTrue(hospital.listSections().contains(section));
	}

	@Test(expected = DuplicateInfoException.class)
	public void testAddDuplicateSection() throws DuplicateInfoException {
		int id = 1;
		String name = "Merkez Hastanesi";
		Hospital hospital = new Hospital(id, name);
		Section section1 = new Section(101, "Kardiyoloji", 2);
		Section section2 = new Section(101, "Dahiliye", 2);
		hospital.addSection(section1);
		hospital.addSection(section2);

	}

	@Test
	public void testHospitalNotNull() {
		int id = 1;
		String name = "Merkez Hastanesi";
		Hospital hospital = new Hospital(id, name);
		assertNotNull(hospital.listSections());
		assertNotNull(hospital.toString());
	}

}