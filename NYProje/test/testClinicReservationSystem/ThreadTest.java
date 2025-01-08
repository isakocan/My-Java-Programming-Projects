package testClinicReservationSystem;

import clinicReservationSystem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class ThreadTest {

	private CRS crs;
	private ExecutorService executor;

	@Before
	public void setUp() throws Exception {
		crs = new CRS();
		// Önceden bazı veriler ekleyelim
		crs.addPatient(new Patient("Ahmet", 123));
		crs.addHospital(new Hospital(1, "Merkez Hastanesi"));
		crs.getHospitals().get(1).addSection(new Section(101, "Kardiyoloji", 1));
		crs.getHospitals().get(1).getSection(101).addDoctor(new Doctor("Dr. Mehmet", 321, 11));
		crs.getHospitals().get(1).getSection(101).addDoctor(new Doctor("Dr. Ayşe", 213, 22));
		executor = Executors.newFixedThreadPool(2);
	}

	private boolean testMakeAppointment(long patientId, int hospitalId, int sectionId, int diplomaId, String dateString)
			throws Exception {
		final boolean[] success = { false };
		Callable<Boolean> task = () -> {
			synchronized (crs) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					Date desiredDate = dateFormat.parse(dateString);
					success[0] = crs.makeRandezvous(patientId, hospitalId, sectionId, diplomaId, desiredDate);
					System.out.println(diplomaId + " id'li doktora " + dateFormat.format(desiredDate) +
							" tarihi için randevu alınma durumu: " + success[0]);

				} catch (Exception e) {
					e.printStackTrace();

				}
				return success[0];
			}

		};
		Future<Boolean> future = executor.submit(task);
		return future.get();

	}

	@After
	public void tearDown() throws Exception {
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}

	@Test
	public void testConcurrentAppointments_SameDay_SecondAppointmentFails() throws Exception {
		System.out.println("\nTest 1: aynı gün aynı doktor");
		boolean result1 = testMakeAppointment(123, 1, 101, 11, "20-12-2024");
		boolean result2 = testMakeAppointment(123, 1, 101, 11, "20-12-2024");
		assertTrue(result1); // İlk randevu alınmalı
		assertFalse(result2); // İkinci randevu aynı gün alınamamalı
	}

	@Test
	public void testConcurrentAppointments_DifferentDays_BothAppointmentsSucceed() throws Exception {
		System.out.println("\nTest 2: farklı gün aynı doktor");
		boolean result1 = testMakeAppointment(123, 1, 101, 11, "20-12-2024");
		boolean result2 = testMakeAppointment(123, 1, 101, 11, "21-12-2024");
		assertTrue(result1); // İlk randevu alınmalı
		assertTrue(result2); // İkinci randevu farklı gün olduğu için alınmalı
	}

	@Test
	public void testConcurrentAppointments_DifferentDoctorSameDay_BothAppointmentsSucceed() throws Exception {
		System.out.println("\nTest 3 : aynı gün farklı doktor");
		boolean result1 = testMakeAppointment(123, 1, 101, 11, "20-12-2024");
		boolean result2 = testMakeAppointment(123, 1, 101, 22, "20-12-2024");
		assertTrue(result1); // İlk randevu alınmalı
		assertTrue(result2); // İkinci randevu aynı gün farklı doktor olduğu için alınmalı

	}

}