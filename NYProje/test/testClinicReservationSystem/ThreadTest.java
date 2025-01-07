package testClinicReservationSystem;

import clinicReservationSystem.*;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.*;

public class ThreadTest {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private PrintStream originalOut = System.out;
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private ByteArrayInputStream inContent;

	@Test
	public void testCRSGUIMakeAppointmentThread() throws InterruptedException, ParseException, DuplicateInfoException {

		Patient patient1 = new Patient("Test Patient 1", 123456789);
		Hospital hospital1 = new Hospital(1, "Test Hospital 1");
		Section section1 = new Section(101, "Test Section 1", 2);
		Doctor doctor1 = new Doctor("Test Doctor 1", 11112222333L, 456);
		section1.addDoctor(doctor1);
		hospital1.addSection(section1);
		CRS crs = new CRS();
		crs.addPatient(patient1);
		crs.addHospital(hospital1);

		new CRSGUI();

		Thread.sleep(100);
		String input = "1\n1\n101\n456\n01-01-2024\n";
		inContent = new ByteArrayInputStream(input.getBytes());
		System.setIn(inContent);
		System.setOut(new PrintStream(outContent));
		new CRSTerminal();
		Thread.sleep(100);
		assertTrue(outContent.toString().contains("Randevu başarıyla oluşturuldu."));
		System.setIn(System.in);
		System.setOut(originalOut);

	}

	@Test
	public void testCRSGUIViewSystemThread() throws InterruptedException, ParseException, DuplicateInfoException {
		Patient patient1 = new Patient("Test Patient 1", 123456789);
		Hospital hospital1 = new Hospital(1, "Test Hospital 1");
		Section section1 = new Section(101, "Test Section 1", 2);
		Doctor doctor1 = new Doctor("Test Doctor 1", 11112222333L, 456);
		section1.addDoctor(doctor1);
		hospital1.addSection(section1);
		CRS crs = new CRS();
		crs.addPatient(patient1);
		crs.addHospital(hospital1);
		Date date = dateFormat.parse("01-01-2024");
		crs.makeRandezvous(123456789L, 1, 101, 456, date);

		new CRSGUI();
		Thread.sleep(100);

		String input = "6\n";
		inContent = new ByteArrayInputStream(input.getBytes());
		System.setIn(inContent);
		System.setOut(new PrintStream(outContent));
		new CRSTerminal();
		Thread.sleep(100);
		assertTrue(outContent.toString().contains("Hastalar:"));
		assertTrue(outContent.toString().contains("Hastaneler:"));
		assertTrue(outContent.toString().contains("Randevular:"));
		System.setIn(System.in);
		System.setOut(originalOut);
	}

	@Test
	public void testCRSTerminalMakeAppointmentThread()
			throws InterruptedException, ParseException, DuplicateInfoException {
		Patient patient1 = new Patient("Test Patient 1", 123456789L);
		Hospital hospital1 = new Hospital(1, "Test Hospital 1");
		Section section1 = new Section(101, "Test Section 1", 2);
		Doctor doctor1 = new Doctor("Test Doctor 1", 11112222333L, 456);
		section1.addDoctor(doctor1);
		hospital1.addSection(section1);
		CRS crs = new CRS();
		crs.addPatient(patient1);
		crs.addHospital(hospital1);

		String input = "5\n123456789\n1\n101\n456\n01-01-2024\n";
		inContent = new ByteArrayInputStream(input.getBytes());
		System.setIn(inContent);
		System.setOut(new PrintStream(outContent));
		new CRSTerminal();

		Thread.sleep(100);
		assertTrue(outContent.toString().contains("Randevu başarıyla oluşturuldu."));
		System.setIn(System.in);
		System.setOut(originalOut);
	}

	@Test
	public void testCRSTerminalPrintSystemInfoThread()
			throws InterruptedException, ParseException, DuplicateInfoException {
		Patient patient1 = new Patient("Test Patient 1", 123456789);
		Hospital hospital1 = new Hospital(1, "Test Hospital 1");
		Section section1 = new Section(101, "Test Section 1", 2);
		Doctor doctor1 = new Doctor("Test Doctor 1", 11112222333L, 456);
		section1.addDoctor(doctor1);
		hospital1.addSection(section1);
		CRS crs = new CRS();
		crs.addPatient(patient1);
		crs.addHospital(hospital1);
		Date date = dateFormat.parse("01-01-2024");
		crs.makeRandezvous(123456789L, 1, 101, 456, date);

		String input = "6\n";
		inContent = new ByteArrayInputStream(input.getBytes());
		System.setIn(inContent);
		System.setOut(new PrintStream(outContent));
		new CRSTerminal();
		Thread.sleep(100);
		assertTrue(outContent.toString().contains("Hastalar:"));
		assertTrue(outContent.toString().contains("Hastaneler:"));
		assertTrue(outContent.toString().contains("Randevular:"));
		System.setIn(System.in);
		System.setOut(originalOut);
	}
}