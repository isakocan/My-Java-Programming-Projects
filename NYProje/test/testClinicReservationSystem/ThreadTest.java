package testClinicReservationSystem;

import clinicReservationSystem.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

	private CRSTerminalTest terminal;
	private CRS crs;
	private StringBuilder output;

	@Before
	public void setUp() throws Exception {
		crs = new CRS();
		output = new StringBuilder();
		terminal = new CRSTerminalTest(crs, output);

		// Önceden bazı veriler ekleyelim
		terminal.addPatient("Ahmet", 123);
		terminal.addHospital(101, "Merkez Hastanesi");
		terminal.addSection(101, 201, "Kardiyoloji", 5);
		terminal.addDoctor(101, 201, "Dr. Mehmet", 11122233344L, 301);
		terminal.addDoctor(101, 201, "Dr. Ayşe", 11122233355L, 302);

	}

	@Test
	public void testConcurrentAppointments() throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(2); // 2 thread ile test edelim

		// 1. randevu
		executor.submit(() -> {
			try {
				output.append("Thread 1: Randevu almaya çalışıyor...\n");
				boolean result = terminal.makeAppointment(123, 101, 201, 301, "20-12-2024");
				output.append("Thread 1: Randevu alma sonucu: " + result + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// 2. randevu aynı gün
		executor.submit(() -> {
			try {
				output.append("Thread 2: Randevu almaya çalışıyor...\n");
				boolean result = terminal.makeAppointment(123, 101, 201, 301, "20-12-2024");
				output.append("Thread 2: Randevu alma sonucu: " + result + "\n");

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);

		// 1 randevu alınmış olması lazım 2. randevu alınamaması lazım aynı gün
		long appointmentCount = crs.getRendezvous().stream().count();
		assertEquals(1, appointmentCount);

		assertTrue(output.toString().contains("Thread 1: Randevu alma sonucu: true"));
		assertTrue(output.toString().contains("Thread 2: Randevu alma sonucu: false")
				|| output.toString().contains("Thread 2: Randevu alma sonucu: null"));

	}

	@Test
	public void testConcurrentAppointments_DifferentDays() throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(2); // 2 thread ile test edelim

		// 1. randevu
		executor.submit(() -> {
			try {
				output.append("Thread 1: Randevu almaya çalışıyor...\n");
				boolean result = terminal.makeAppointment(123, 101, 201, 301, "20-12-2024");
				output.append("Thread 1: Randevu alma sonucu: " + result + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// 2. randevu aynı doktor farklı gün
		executor.submit(() -> {
			try {
				output.append("Thread 2: Randevu almaya çalışıyor...\n");
				boolean result = terminal.makeAppointment(123, 101, 201, 301, "21-12-2024");
				output.append("Thread 2: Randevu alma sonucu: " + result + "\n");

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);

		// İki randevuda alınmış olması lazım çünkü 2 farklı gün
		long appointmentCount = crs.getRendezvous().stream().count();
		assertEquals(2, appointmentCount);

		assertTrue(output.toString().contains("Thread 1: Randevu alma sonucu: true"));
		assertTrue(output.toString().contains("Thread 2: Randevu alma sonucu: true"));

	}

	@Test
	public void testConcurrentAppointments_DifferentDoctorSameDay() throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(2); // 2 thread ile test edelim

		// 1. randevu
		executor.submit(() -> {
			try {
				output.append("Thread 1: Randevu almaya çalışıyor...\n");
				boolean result = terminal.makeAppointment(123, 101, 201, 301, "20-12-2024");
				output.append("Thread 1: Randevu alma sonucu: " + result + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// 2. randevu aynı gün farklı doktor
		executor.submit(() -> {
			try {
				output.append("Thread 2: Randevu almaya çalışıyor...\n");
				boolean result = terminal.makeAppointment(123, 101, 201, 302, "20-12-2024");
				output.append("Thread 2: Randevu alma sonucu: " + result + "\n");

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);

		// İki randevuda alınmış olması lazım çünkü 2 farklı doktor aynı gün
		long appointmentCount = crs.getRendezvous().stream().count();
		assertEquals(2, appointmentCount);

		assertTrue(output.toString().contains("Thread 1: Randevu alma sonucu: true"));
		assertTrue(output.toString().contains("Thread 2: Randevu alma sonucu: true"));

	}

	@Test
	public void testPrintSystemInfo() throws Exception {
		terminal.printSystemInfo();
		String systemInfo = output.toString();
		assertNotNull(systemInfo);
		System.out.print(systemInfo);
		assertTrue(systemInfo.contains("Hastalar:"));
		assertTrue(systemInfo.contains("Hastaneler:"));
		assertTrue(systemInfo.contains("Randevular:"));

	}

}