package testClinicReservationSystem;

import clinicReservationSystem.*;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CRSTest {

	@Test
	public void testCRSNotNull() {
		CRS crs = new CRS();
		assertNotNull(crs.getHospitals());
		assertNotNull(crs.getPatients());
		assertNotNull(crs.getRendezvous());
	}

	@Test
	public void testAddPatient() throws DuplicateInfoException {
		CRS crs = new CRS();
		Patient patient = new Patient("Ali Veli", 12345678901L);
		crs.addPatient(patient);
		assertTrue(crs.getPatients().containsValue(patient));
	}

	@Test(expected = DuplicateInfoException.class)
	public void testAddDuplicatePatient() throws DuplicateInfoException {
		CRS crs = new CRS();
		Patient patient1 = new Patient("Ali Veli", 12345678901L);
		Patient patient2 = new Patient("Ali Veli", 12345678901L);
		crs.addPatient(patient1);
		crs.addPatient(patient2);

	}

	@Test
	public void testAddHospital() throws DuplicateInfoException {
		CRS crs = new CRS();
		Hospital hospital = new Hospital(1, "Merkez Hastanesi");
		crs.addHospital(hospital);
		assertTrue(crs.getHospitals().containsValue(hospital));
	}

	@Test(expected = DuplicateInfoException.class)
	public void testAddDuplicateHospital() throws DuplicateInfoException {
		CRS crs = new CRS();
		Hospital hospital1 = new Hospital(1, "Merkez Hastanesi");
		Hospital hospital2 = new Hospital(1, "Şehir Hastanesi");
		crs.addHospital(hospital1);
		crs.addHospital(hospital2);

	}

	@Test
	public void testMakeRendezvous() throws ParseException, IDException {
		CRS crs = new CRS();
		Patient patient = new Patient("Ali Veli", 12345678901L);
		crs.addPatient(patient);
		Hospital hospital = new Hospital(1, "Merkez Hastanesi");
		crs.addHospital(hospital);
		Section section = new Section(101, "Kardiyoloji", 2);
		hospital.addSection(section);
		Doctor doctor = new Doctor("Ayşe Kaya", 11223344556L, 777);
		section.addDoctor(doctor);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date desiredDate = dateFormat.parse("10-12-2024");
		assertTrue(crs.makeRandezvous(12345678901L, 1, 101, 777, desiredDate));

	}

	@Test(expected = IDException.class)
	public void testMakeRendezvousWithWrongId() throws ParseException, IDException {
		CRS crs = new CRS();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date desiredDate = dateFormat.parse("10-12-2024");
		crs.makeRandezvous(11111111111L, 1, 101, 777, desiredDate);

	}

}