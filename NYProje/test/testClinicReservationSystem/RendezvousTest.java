package testClinicReservationSystem;

import clinicReservationSystem.Doctor;
import clinicReservationSystem.Patient;
import clinicReservationSystem.Rendezvous;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RendezvousTest {

	@Test
	public void testRendezvousCreation() {
		Patient patient = new Patient("Ahmet Yılmaz", 12345678901L);
		Doctor doctor = new Doctor("Ayşe Kaya", 11223344556L, 777);
		Date date = new Date();
		Rendezvous rendezvous = new Rendezvous(patient, date, doctor);
		assertEquals(patient, rendezvous.getPatient());
		assertEquals(doctor, rendezvous.getDoctor());
		assertEquals(date, rendezvous.getDateTime());

	}

	@Test
	public void testRendezvousSetDateTime() {
		Patient patient = new Patient("Ahmet Yılmaz", 12345678901L);
		Doctor doctor = new Doctor("Ayşe Kaya", 11223344556L, 777);
		Date date1 = new Date();
		Rendezvous rendezvous = new Rendezvous(patient, date1, doctor);
		Date date2 = new Date();
		rendezvous.setDateTime(date2);
		assertEquals(date2, rendezvous.getDateTime());
	}

	@Test
	public void testRendezvousSetPatient() {
		Patient patient1 = new Patient("Ahmet Yılmaz", 12345678901L);
		Doctor doctor = new Doctor("Ayşe Kaya", 11223344556L, 777);
		Date date1 = new Date();
		Rendezvous rendezvous = new Rendezvous(patient1, date1, doctor);
		Patient patient2 = new Patient("Ayşe Kaya", 98765432109L);
		rendezvous.setPatient(patient2);
		assertEquals(patient2, rendezvous.getPatient());
	}

	@Test
	public void testRendezvousSetDoctor() {
		Patient patient1 = new Patient("Ahmet Yılmaz", 12345678901L);
		Doctor doctor1 = new Doctor("Ayşe Kaya", 11223344556L, 777);
		Date date1 = new Date();
		Rendezvous rendezvous = new Rendezvous(patient1, date1, doctor1);
		Doctor doctor2 = new Doctor("Zeynep Yılmaz", 55667788990L, 999);
		rendezvous.setDoctor(doctor2);
		assertEquals(doctor2, rendezvous.getDoctor());
	}

	@Test
	public void testRendezvousToString() {
		Patient patient = new Patient("Ahmet Yılmaz", 12345678901L);
		Doctor doctor = new Doctor("Ayşe Kaya", 11223344556L, 777);
		Date date = new Date();
		Rendezvous rendezvous = new Rendezvous(patient, date, doctor);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = dateFormat.format(date);
		String expectedToString = "Randevu tarihi: " + formattedDate + ", hasta adı: " + patient.getName()
				+ ", doktor adı: " + doctor.getName();
		assertEquals(expectedToString, rendezvous.toString());
	}

	@Test
	public void testRendezvousNotNull() {
		Patient patient = new Patient("Ahmet Yılmaz", 12345678901L);
		Doctor doctor = new Doctor("Ayşe Kaya", 11223344556L, 777);
		Date date = new Date();
		Rendezvous rendezvous = new Rendezvous(patient, date, doctor);
		assertNotNull(rendezvous.getDoctor());
		assertNotNull(rendezvous.getPatient());
		assertNotNull(rendezvous.getDateTime());
		assertNotNull(rendezvous.toString());
	}

}