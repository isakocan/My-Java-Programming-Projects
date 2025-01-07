package testClinicReservationSystem;

import clinicReservationSystem.Person;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersonTest {
	@Test
	public void testPersonCreation() {
		String name = "Ali Veli";
		long nationalId = 12345678901L;
		Person person = new Person(name, nationalId);
		assertEquals(name, person.getName());
		assertEquals(nationalId, person.getNationalId());
	}

	@Test
	public void testPersonToString() {
		String name = "Ayşe Kaya";
		long nationalId = 98765432109L;
		Person person = new Person(name, nationalId);
		String expectedToString = "İsim: " + name + ", Kimlik no: " + nationalId;
		assertEquals(expectedToString, person.toString());
	}

	@Test
	public void testPersonNameChange() {
		String initialName = "Veli Demir";
		long nationalId = 55555555555L;
		Person person = new Person(initialName, nationalId);
		String newName = "Veli Yılmaz";
		person.setName(newName);
		assertEquals(newName, person.getName());

	}

	@Test
	public void testPersonNotNull() {
		String name = "Veli Demir";
		long nationalId = 55555555555L;
		Person person = new Person(name, nationalId);
		assertNotNull(person);
		assertNotNull(person.getName());
		assertNotNull(person.toString());
	}

}