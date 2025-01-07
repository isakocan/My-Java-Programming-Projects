package testClinicReservationSystem;

import clinicReservationSystem.IDException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class IDExceptionTest {

	@Test
	public void testIDExceptionCreation() {
		String message = "Id not found";
		IDException ex = new IDException(message);
		assertNotNull(ex);
		assertEquals(message, ex.getMessage());
	}

	@Test
	public void testIDExceptionThrow() {
		String message = "Id not found";
		assertThrows(IDException.class, () -> {
			throw new IDException(message);
		});
	}

}