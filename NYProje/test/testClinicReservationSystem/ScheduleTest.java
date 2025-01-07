package testClinicReservationSystem;

import clinicReservationSystem.Patient;
import clinicReservationSystem.Schedule;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ScheduleTest {

	@Test
	public void testScheduleCreation() {
		int maxPatientPerDay = 5;
		Schedule schedule = new Schedule(maxPatientPerDay);
		assertEquals(maxPatientPerDay, schedule.getMaxPatientPerDay());
		assertNotNull(schedule.getSessions());
	}

	@Test
	public void testAddRendezvous() {
		int maxPatientPerDay = 2;
		Schedule schedule = new Schedule(maxPatientPerDay);
		Patient patient1 = new Patient("Ali Veli", 12345678901L);
		Date date1 = new Date();
		assertTrue(schedule.addRendezvous(patient1, date1));
		Patient patient2 = new Patient("Ayşe Kaya", 98765432109L);
		Date date2 = new Date();
		assertTrue(schedule.addRendezvous(patient2, date2));
		Patient patient3 = new Patient("Veli Can", 55667788990L);
		Date date3 = new Date();
		assertFalse(schedule.addRendezvous(patient3, date3));
	}

	@Test
	public void testScheduleSetMaxPatientPerDay() {
		int maxPatientPerDay = 5;
		Schedule schedule = new Schedule(maxPatientPerDay);
		int newMaxPatientPerDay = 10;
		schedule.setMaxPatientPerDay(newMaxPatientPerDay);
		assertEquals(newMaxPatientPerDay, schedule.getMaxPatientPerDay());
	}

	@Test
	public void testScheduleNotNull() {
		int maxPatientPerDay = 5;
		Schedule schedule = new Schedule(maxPatientPerDay);
		assertNotNull(schedule.getSessions());
	}
}