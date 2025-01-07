package testClinicReservationSystem;

import clinicReservationSystem.DuplicateInfoException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class DuplicateInfoExceptionTest {

	@Test
	public void testDuplicateInfoExceptionCreation() {
		String message = "Duplicate information found";
		DuplicateInfoException ex = new DuplicateInfoException(message);
		assertNotNull(ex);
		assertEquals(message, ex.getMessage());

	}

	@Test
	public void testDuplicateInfoExceptionThrow() {
		String message = "Duplicate information found";
		assertThrows(DuplicateInfoException.class, () -> {
			throw new DuplicateInfoException(message);
		});
	}
}